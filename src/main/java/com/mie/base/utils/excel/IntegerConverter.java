package com.mie.base.utils.excel;

import jxl.Cell;
import jxl.Sheet;

import java.util.List;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class IntegerConverter implements StrToObjConverter<Integer>, ObjToStrConverter<Integer> {
    public IntegerConverter() {
    }

    public Integer convert(String content, List<Cell> rows, Sheet sheet) {
        return Integer.valueOf(content);
    }

    public String convert(Integer t, Object item, int index) {
        return t.toString();
    }
}
