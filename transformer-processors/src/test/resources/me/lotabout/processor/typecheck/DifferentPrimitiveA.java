package me.lotabout.processor.typecheck;

import me.lotabout.annotation.Transformer;

@Transformer(to = {DifferentPrimitiveB.class})
public class DifferentPrimitiveA {
    int fieldA;

    public int getFieldA() {
        return fieldA;
    }

    public void setFieldA(int fieldA) {
        this.fieldA = fieldA;
    }
}