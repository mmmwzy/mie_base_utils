package com.mie.base.utils.excel;

/**
 * Created by WangRicky on 2018/5/17.
 */
public interface ObjToStrConverter<T> {
    String convert(T var1, Object var2, int var3);
}
