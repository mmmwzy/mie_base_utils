package com.mie.base.utils.excel;

import jxl.write.WritableCellFormat;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class TitleAndModelKey {
    private Integer columIndex;
    private String title;
    private String modelKey;
    private String defaultValue = "";
    private boolean required = false;
    private StrToObjConverter toObjConverter;
    private ObjToStrConverter toStrConverter;
    private WritableCellFormat titleFormat;
    private WritableCellFormat contentFormat;

    public TitleAndModelKey() {
    }

    public TitleAndModelKey(int columIndex, String modelKey) {
        this.columIndex = columIndex;
        this.modelKey = modelKey;
    }

    public TitleAndModelKey(String title, String modelKey) {
        this.title = title;
        this.modelKey = modelKey;
    }

    public TitleAndModelKey(String title, String modelKey, StrToObjConverter toObjConverter) {
        this.title = title;
        this.modelKey = modelKey;
        this.toObjConverter = toObjConverter;
    }

    public TitleAndModelKey(String title, String modelKey, ObjToStrConverter toStrConverter) {
        this.title = title;
        this.modelKey = modelKey;
        this.toStrConverter = toStrConverter;
    }

    public TitleAndModelKey(int columIndex, String modelKey, ObjToStrConverter toStrConverter) {
        this.columIndex = columIndex;
        this.modelKey = modelKey;
        this.toStrConverter = toStrConverter;
    }

    public TitleAndModelKey(int columIndex, String modelKey, StrToObjConverter toObjConverter) {
        this.columIndex = columIndex;
        this.modelKey = modelKey;
        this.toObjConverter = toObjConverter;
    }

    public TitleAndModelKey(Integer columIndex, String title, String modelKey, String defaultValue, StrToObjConverter toObjConverter, ObjToStrConverter toStrConverter, WritableCellFormat titleFormat, WritableCellFormat contentFormat) {
        this.columIndex = columIndex;
        this.title = title;
        this.modelKey = modelKey;
        this.defaultValue = defaultValue;
        this.toObjConverter = toObjConverter;
        this.toStrConverter = toStrConverter;
        this.titleFormat = titleFormat;
        this.contentFormat = contentFormat;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModelKey() {
        return this.modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public Integer getColumIndex() {
        return this.columIndex;
    }

    public void setColumIndex(Integer columIndex) {
        this.columIndex = columIndex;
    }

    public StrToObjConverter getToObjConverter() {
        return this.toObjConverter;
    }

    public void setToObjConverter(StrToObjConverter toObjConverter) {
        this.toObjConverter = toObjConverter;
    }

    public ObjToStrConverter getToStrConverter() {
        return this.toStrConverter;
    }

    public void setToStrConverter(ObjToStrConverter toStrConverter) {
        this.toStrConverter = toStrConverter;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public WritableCellFormat getTitleFormat() {
        return this.titleFormat;
    }

    public void setTitleFormat(WritableCellFormat titleFormat) {
        this.titleFormat = titleFormat;
    }

    public WritableCellFormat getContentFormat() {
        return this.contentFormat;
    }

    public void setContentFormat(WritableCellFormat contentFormat) {
        this.contentFormat = contentFormat;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}

