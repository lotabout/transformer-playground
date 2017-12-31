package me.lotabout.processor.model;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public interface FieldEntry {
    String getName();
    TypeEntry getType();
    String getGetter();
    String getSetter();

    Element getElement();

    void transformTo(FieldEntry to, TransformerClass env, MethodSpec.Builder builder);
}
