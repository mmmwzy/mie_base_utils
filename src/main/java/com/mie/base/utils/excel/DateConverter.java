package com.mie.base.utils.excel;

import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class DateConverter implements StrToObjConverter<Date>, ObjToStrConverter<Date> {
    private static final SimpleDateFormat DEFAULT_DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateFormat;
    private String dateFormat;

    public String getDateFormat() {
        return this.dateFormat;
    }

    public DateConverter() {
    }

    public DateConverter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateConverter setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public Date convert(String content, List<Cell> rows, Sheet sheet) throws Exception {
        if (StringUtils.isBlank(this.dateFormat)) {
            this.simpleDateFormat = DEFAULT_DATE_FORMATE;
        } else {
            this.simpleDateFormat = new SimpleDateFormat(this.dateFormat);
        }

        return this.simpleDateFormat.parse(content);
    }

    public String convert(Date t, Object item, int index) {
        if (StringUtils.isBlank(this.dateFormat)) {
            this.simpleDateFormat = DEFAULT_DATE_FORMATE;
        } else {
            this.simpleDateFormat = new SimpleDateFormat(this.dateFormat);
        }

        return this.simpleDateFormat.format(t);
    }
}

