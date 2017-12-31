package me.lotabout.processor.typecheck;

import me.lotabout.annotation.Transformer;

@Transformer(to = {PrimitiveAndWrapperClassB.class})
public class PrimitiveAndWrapperClassA {
    boolean fieldBool;
    byte fieldByte;
    char fieldChar;
    short fieldShort;
    int fieldInt;
    long fieldLong;
    float fieldFloat;
    double fieldDouble;

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
}
