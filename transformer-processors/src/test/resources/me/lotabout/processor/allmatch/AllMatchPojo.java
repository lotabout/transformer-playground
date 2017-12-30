package me.lotabout.pojo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class AllMatchPojo {
    // primitive types
    boolean fieldBool;
    byte fieldByte;
    char fieldChar;
    short fieldShort;
    int fieldInt;
    long fieldLong;
    float fieldFloat;
    double fieldDouble;

    // primitive Classes
    Boolean fieldBoolean;
    Byte fieldByteClass;
    Character fieldCharacter;
    Short fieldShortClass;
    Integer fieldInteger;
    Long fieldLongClass;
    Float fieldFloatClass;
    Double fieldDoubleClass;

    // IdenticalClass
    ZonedDateTime time;

    // array not supported yet

    // (nested) list of primitive types
    List<String> fieldList;
    List<List<String>> fieldNestedList;
    List<AllMatchPojo> fieldListClass;

    // (nested) map;
    Map<String, String> fieldMap;
    Map<String, Map<String, String>> fieldNestedMap;
    Map<Integer, AllMatchPojo> fieldMapClass;

    public boolean isFieldBool() {
        return fieldBool;
    }

    public void setFieldBool(boolean fieldBool) {
        this.fieldBool = fieldBool;
    }

    public byte getFieldByte() {
        return fieldByte;
    }

    public void setFieldByte(byte fieldByte) {
        this.fieldByte = fieldByte;
    }

    public char getFieldChar() {
        return fieldChar;
    }

    public void setFieldChar(char fieldChar) {
        this.fieldChar = fieldChar;
    }

    public short getFieldShort() {
        return fieldShort;
    }

    public void setFieldShort(short fieldShort) {
        this.fieldShort = fieldShort;
    }

    public int getFieldInt() {
        return fieldInt;
    }

    public void setFieldInt(int fieldInt) {
        this.fieldInt = fieldInt;
    }

    public long getFieldLong() {
        return fieldLong;
    }

    public void setFieldLong(long fieldLong) {
        this.fieldLong = fieldLong;
    }

    public float getFieldFloat() {
        return fieldFloat;
    }

    public void setFieldFloat(float fieldFloat) {
        this.fieldFloat = fieldFloat;
    }

    public double getFieldDouble() {
        return fieldDouble;
    }

    public void setFieldDouble(double fieldDouble) {
        this.fieldDouble = fieldDouble;
    }

    public Boolean getFieldBoolean() {
        return fieldBoolean;
    }

    public void setFieldBoolean(Boolean fieldBoolean) {
        this.fieldBoolean = fieldBoolean;
    }

    public Byte getFieldByteClass() {
        return fieldByteClass;
    }

    public void setFieldByteClass(Byte fieldByteClass) {
        this.fieldByteClass = fieldByteClass;
    }

    public Character getFieldCharacter() {
        return fieldCharacter;
    }

    public void setFieldCharacter(Character fieldCharacter) {
        this.fieldCharacter = fieldCharacter;
    }

    public Short getFieldShortClass() {
        return fieldShortClass;
    }

    public void setFieldShortClass(Short fieldShortClass) {
        this.fieldShortClass = fieldShortClass;
    }

    public Integer getFieldInteger() {
        return fieldInteger;
    }

    public void setFieldInteger(Integer fieldInteger) {
        this.fieldInteger = fieldInteger;
    }

    public Long getFieldLongClass() {
        return fieldLongClass;
    }

    public void setFieldLongClass(Long fieldLongClass) {
        this.fieldLongClass = fieldLongClass;
    }

    public Float getFieldFloatClass() {
        return fieldFloatClass;
    }

    public void setFieldFloatClass(Float fieldFloatClass) {
        this.fieldFloatClass = fieldFloatClass;
    }

    public Double getFieldDoubleClass() {
        return fieldDoubleClass;
    }

    public void setFieldDoubleClass(Double fieldDoubleClass) {
        this.fieldDoubleClass = fieldDoubleClass;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public List<List<String>> getFieldNestedList() {
        return fieldNestedList;
    }

    public void setFieldNestedList(List<List<String>> fieldNestedList) {
        this.fieldNestedList = fieldNestedList;
    }

    public List<AllMatchPojo> getFieldListClass() {
        return fieldListClass;
    }

    public void setFieldListClass(List<AllMatchPojo> fieldListClass) {
        this.fieldListClass = fieldListClass;
    }

    public Map<String, String> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, Map<String, String>> getFieldNestedMap() {
        return fieldNestedMap;
    }

    public void setFieldNestedMap(Map<String, Map<String, String>> fieldNestedMap) {
        this.fieldNestedMap = fieldNestedMap;
    }

    public Map<Integer, AllMatchPojo> getFieldMapClass() {
        return fieldMapClass;
    }

    public void setFieldMapClass(Map<Integer, AllMatchPojo> fieldMapClass) {
        this.fieldMapClass = fieldMapClass;
    }
}
