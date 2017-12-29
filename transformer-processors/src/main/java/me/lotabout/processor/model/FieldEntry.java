package me.lotabout.processor.model;

import javax.lang.model.element.Element;

public interface FieldEntry {
    String getName();
    TypeEntry getType();
    String getGetter();
    String getSetter();

    Element getElement();
}
