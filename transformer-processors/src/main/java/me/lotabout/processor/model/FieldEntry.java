package me.lotabout.processor.model;

public interface FieldEntry {
    String getName();
    TypeEntry getType();
    String getGetter();
    String getSetter();
}
