package com.mie.base.utils.excel;

import jxl.Cell;
import jxl.Sheet;

import java.util.List;

/**
 * Created by WangRicky on 2018/5/17.
 */
public interface StrToObjConverter<T> {
    T convert(String var1, List<Cell> var2, Sheet var3) throws Exception;
}
