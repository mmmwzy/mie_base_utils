package com.mie.base.utils.excel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class ConverterFactory {
    private final Map<String, StrToObjConverter> toObjMap = new Hashtable();
    private final Map<String, ObjToStrConverter> toStrMap = new Hashtable();
    private static ConverterFactory factory = new ConverterFactory();

    private void registe() {
        this.toObjMap.put(Integer.class.toString(), new IntegerConverter());
        this.toObjMap.put(BigDecimal.class.toString(), new BigDecimalConverter());
        this.toObjMap.put(Date.class.toString(), new DateConverter());
        this.toStrMap.put(Integer.class.toString(), new IntegerConverter());
        this.toStrMap.put(BigDecimal.class.toString(), new BigDecimalConverter());
        this.toStrMap.put(Date.class.toString(), new DateConverter());
    }

    public static StrToObjConverter getDefaultToObjConverter(Class clazz) {
        return !clazz.equals(Integer.class) && !clazz.equals(BigDecimal.class) && !clazz.equals(Date.class) ? null : (StrToObjConverter)factory.getToObjMap().get(clazz.toString());
    }

    public static ObjToStrConverter getDefaultToStrConverter(Class clazz) {
        return !clazz.equals(Integer.class) && !clazz.equals(BigDecimal.class) && !clazz.equals(Date.class) ? null : (ObjToStrConverter)factory.getToStrMap().get(clazz.toString());
    }

    private ConverterFactory() {
        this.registe();
    }

    public Map<String, StrToObjConverter> getToObjMap() {
        return this.toObjMap;
    }

    public Map<String, ObjToStrConverter> getToStrMap() {
        return this.toStrMap;
    }
}

