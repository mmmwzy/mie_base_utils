package com.mie.base.utils.excel;

import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class BigDecimalConverter implements StrToObjConverter<BigDecimal>, ObjToStrConverter<BigDecimal> {
    public BigDecimalConverter() {
    }

    public BigDecimal convert(String content, List<Cell> rows, Sheet sheet) {
        content = content.trim().replaceAll("\\s+", "");
        if (StringUtils.isEmpty(content)) {
            content = "0";
        }

        BigDecimal num = null;

        try {
            num = new BigDecimal(content);
            return num;
        } catch (Exception var6) {
            throw new IllegalArgumentException("'" + content + "'" + "无法转为数字!");
        }
    }

    public String convert(BigDecimal t, Object item, int index) {
        return t.toString();
    }
}

