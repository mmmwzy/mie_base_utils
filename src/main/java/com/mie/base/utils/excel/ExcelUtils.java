package com.mie.base.utils.excel;

import com.mie.base.utils.reflect.ReflectUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class ExcelUtils {
    private static final SimpleDateFormat DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ExcelUtils() {
    }

    public static WritableWorkbook exportDataToExcel(OutputStream os, List<TitleAndModelKey> titleAndModelKeys, List<? extends Object> data, String sheetTitle, String headTitle, int sheetIndex) throws IOException, RowsExceededException, WriteException, ParseException {
        return exportDataToExcel(os, titleAndModelKeys, data, sheetTitle, headTitle, sheetIndex, (WritableWorkbook)null);
    }

    public static WritableWorkbook exportDataToExcel(OutputStream os, List<TitleAndModelKey> titleAndModelKeys, List<? extends Object> data, String sheetTitle, String headTitle, int sheetIndex, WritableWorkbook writeablebook) throws IOException, RowsExceededException, WriteException, ParseException {
        if (CollectionUtils.isEmpty(titleAndModelKeys)) {
            throw new NullPointerException("export setting 'titleAndModelKeys' can not be null");
        } else {
            if (writeablebook == null) {
                writeablebook = Workbook.createWorkbook(os);
            }

            WritableSheet sheet = writeablebook.createSheet(sheetTitle, sheetIndex);
            int headTitleRowIndex = 0;
            int titleRowIndex = 1;
            WritableCellFormat defaultHeadTitleFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 12, WritableFont.BOLD));
            if (StringUtils.isNotBlank(headTitle)) {
                defaultHeadTitleFormat.setAlignment(jxl.format.Alignment.CENTRE);
                Label headLabel = new Label(0, headTitleRowIndex, headTitle, defaultHeadTitleFormat);
                headLabel.setCellFormat(new WritableCellFormat(NumberFormats.TEXT));
                sheet.mergeCells(0, headTitleRowIndex, titleAndModelKeys.size() - 1, headTitleRowIndex);
                sheet.addCell(headLabel);
            } else {
                titleRowIndex = 0;
            }

            int i;
            for(i = 0; i < titleAndModelKeys.size(); ++i) {
                WritableCellFormat titleFormat = ((TitleAndModelKey)titleAndModelKeys.get(i)).getTitleFormat();
                if (titleFormat == null) {
                    titleFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 12));
                    titleFormat.setBackground(jxl.format.Colour.GRAY_25);
                    titleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
                }

                Label label = new Label(i, titleRowIndex, ((TitleAndModelKey)titleAndModelKeys.get(i)).getTitle(), titleFormat);
                sheet.setColumnView(i, 20);
                sheet.addCell(label);
            }

            if (CollectionUtils.isEmpty(data)) {
                return writeablebook;
            } else {
                for(i = 0; data != null && i < data.size(); ++i) {
                    MetaObject metaObject = MetaObject.forObject(data.get(i), ReflectUtils.DEFAULT_OBJECT_FACTORY, ReflectUtils.DEFAULT_OBJECT_WRAPPER_FACTORY, ReflectUtils.DEFAULT_REFLECTOR_FACTORY);

                    for(int j = 0; j < titleAndModelKeys.size(); ++j) {
                        TitleAndModelKey titleAndModelKey = (TitleAndModelKey)titleAndModelKeys.get(j);
                        String key = titleAndModelKey.getModelKey();
                        if (StringUtils.isBlank(key)) {
                            throw new IllegalArgumentException(MessageFormat.format("导入的excel参数异常，titleAndModelKeys中, title{0}, key{1}", titleAndModelKey.getTitle(), titleAndModelKey.getModelKey()));
                        }

                        Object value = null;
                        MetaObject propertyMetaObject = metaObject;
                        String[] keyArray = key.split("\\.");

                        for(int arrayIndex = 0; arrayIndex < keyArray.length; ++arrayIndex) {
                            value = propertyMetaObject.getValue(keyArray[arrayIndex]);
                            propertyMetaObject = MetaObject.forObject(value, ReflectUtils.DEFAULT_OBJECT_FACTORY, ReflectUtils.DEFAULT_OBJECT_WRAPPER_FACTORY, ReflectUtils.DEFAULT_REFLECTOR_FACTORY);
                        }

                        String content = null;
                        if (value == null) {
                            content = titleAndModelKey.getDefaultValue();
                        } else if (titleAndModelKey.getToStrConverter() != null) {
                            content = titleAndModelKey.getToStrConverter().convert(value, data.get(i), i);
                        } else if (ConverterFactory.getDefaultToStrConverter(value.getClass()) != null) {
                            content = ConverterFactory.getDefaultToStrConverter(value.getClass()).convert(value, data.get(i), i);
                        } else {
                            content = String.valueOf(value);
                        }

                        WritableCellFormat contentFormate = titleAndModelKey.getContentFormat();
                        if (contentFormate == null) {
                            contentFormate = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 10));
                            contentFormate.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, Colour.BLACK);
                        }

                        int dataRow = i + titleRowIndex + 1;
                        Label tmpLabel = new Label(j, dataRow, String.valueOf(content), contentFormate);
                        sheet.addCell(tmpLabel);
                    }
                }

                return writeablebook;
            }
        }
    }

    public static <T> List<T> importExcelDataToMap(InputStream in, int sheetIndex, int dataStartRowIndex, int headRowIndex, List<TitleAndModelKey> titleAndModelKeys, Class<T> clazz) throws Exception {
        if (CollectionUtils.isEmpty(titleAndModelKeys)) {
            throw new NullPointerException("export setting 'titleAndModelKeys' can not be null");
        } else {
            Workbook workbook = Workbook.getWorkbook(in);
            Sheet sheet = workbook.getSheet(sheetIndex);
            Cell[] headRow = sheet.getRow(headRowIndex);
            checkExcel(titleAndModelKeys, headRow);
            List<List<Cell>> allRows = getAllRows(sheet, dataStartRowIndex, headRow.length);
            List<T> dataList = new ArrayList();
            int rowCount = allRows.size();

            for(int i = 0; sheet != null && i < rowCount; ++i) {
                List<Cell> row = (List)allRows.get(i);
                T object = clazz.newInstance();
                MetaObject metaObject = MetaObject.forObject(object, ReflectUtils.DEFAULT_OBJECT_FACTORY, ReflectUtils.DEFAULT_OBJECT_WRAPPER_FACTORY, ReflectUtils.DEFAULT_REFLECTOR_FACTORY);

                for(int j = 0; j < titleAndModelKeys.size(); ++j) {
                    TitleAndModelKey titleAndModelKey = (TitleAndModelKey)titleAndModelKeys.get(j);
                    String title = titleAndModelKey.getTitle();
                    Integer index = titleAndModelKey.getColumIndex();
                    if (StringUtils.isBlank(title) && index == null) {
                        throw new IllegalArgumentException("excel 导入导出的操作中，titleAndModelKey配置异常， title 与 columIndex 不能同时为空");
                    }

                    if (index == null) {
                        index = getTitleIndexInRow(headRow, title);
                        titleAndModelKey.setColumIndex(index);
                    }

                    String key = titleAndModelKey.getModelKey();
                    String content = ((Cell)row.get(index.intValue())).getContents();
                    if (StringUtils.isBlank(content)) {
                        if (titleAndModelKey.isRequired()) {
                            throw new NullPointerException(titleAndModelKey.getTitle() + "不能为空值。");
                        }

                        if (StringUtils.isBlank(titleAndModelKey.getDefaultValue())) {
                            continue;
                        }

                        content = titleAndModelKey.getDefaultValue();
                    }

                    Class<?> cla = metaObject.getGetterType(key);
                    StrToObjConverter converter = ((TitleAndModelKey)titleAndModelKeys.get(j)).getToObjConverter();
                    if (converter != null) {
                        metaObject.setValue(key, converter.convert(content, row, sheet));
                    } else if (cla.equals(String.class)) {
                        metaObject.setValue(key, content);
                    } else {
                        converter = ConverterFactory.getDefaultToObjConverter(cla);
                        if (converter == null) {
                            throw new RuntimeException("找不到合适转换器 class[" + cla + "]");
                        }

                        metaObject.setValue(key, converter.convert(content, row, sheet));
                    }
                }

                dataList.add(object);
            }

            return dataList;
        }
    }

    private static List<List<Cell>> getAllRows(Sheet sheet, int dataStartRowIndex, int length) {
        List<List<Cell>> allRows = new ArrayList();
        boolean isEnd = false;

        for(int readIndex = dataStartRowIndex; !isEnd; ++readIndex) {
            if (readIndex == sheet.getRows() + dataStartRowIndex - 1) {
                isEnd = true;
                break;
            }

            List<Cell> row = new ArrayList(length);

            for(int i = 0; i < length; ++i) {
                Cell cell = sheet.getCell(i, readIndex);
                row.add(cell);
            }

            boolean isAllBlank = true;
            Iterator i$ = row.iterator();

            while(i$.hasNext()) {
                Cell cell = (Cell)i$.next();
                if (StringUtils.isNotBlank(cell.getContents())) {
                    isAllBlank = false;
                    break;
                }
            }

            if (isAllBlank) {
                isEnd = true;
                break;
            }

            allRows.add(row);
        }

        return allRows;
    }

    private static Cell[] getFromSheet(Sheet sheet, int startRowIndex, int length) {
        Cell[] cells = new Cell[length];

        for(int i = 0; i < length; ++i) {
            cells[i] = sheet.getCell(i, startRowIndex);
        }

        return cells;
    }

    private static void checkExcel(List<TitleAndModelKey> titleAndModelKeys, Cell[] headRow) {
        for(int j = 0; j < titleAndModelKeys.size(); ++j) {
            String title = ((TitleAndModelKey)titleAndModelKeys.get(j)).getTitle();
            String key = ((TitleAndModelKey)titleAndModelKeys.get(j)).getModelKey();
            int index = getTitleIndexInRow(headRow, title);
            if (index < 0) {
                throw new IllegalArgumentException("excel表格式异常，找不到列[" + title + "]");
            }
        }

    }

    public static int getTitleIndexInRow(Cell[] headRow, String title) {
        if (StringUtils.isBlank(title)) {
            throw new NullPointerException("title can not be null");
        } else {
            int index = -1;

            for(int i = 0; i < headRow.length; ++i) {
                String content = headRow[i].getContents();
                if (StringUtils.equals(content.trim(), title.trim())) {
                    return i;
                }
            }

            return index;
        }
    }

    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey) {
        return new TitleAndModelKey(title, modelKey);
    }

    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey, boolean isRequired) {
        TitleAndModelKey t = new TitleAndModelKey(title, modelKey);
        t.setRequired(isRequired);
        return t;
    }

    public static TitleAndModelKey createTitleAndModelKey(int columIndex, String modelKey) {
        return new TitleAndModelKey(columIndex, modelKey);
    }

    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey, StrToObjConverter toObjConverter) {
        return new TitleAndModelKey(title, modelKey, toObjConverter);
    }

    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey, ObjToStrConverter toStrConverter) {
        return new TitleAndModelKey(title, modelKey, toStrConverter);
    }
}

