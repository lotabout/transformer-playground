package me.lotabout.processor.model;

import java.util.List;

public interface TypeEntry {
    String getQualifiedName();
    String getName();
    String getPackageName();

    List<FieldEntry> getAllFields();
    List<MethodEntry> getAllMethods();

    boolean isPrimitive();
    boolean isCollection();
    boolean isMap();
    boolean isBoolean();

    boolean ableToTransformByMethodFrom(TypeEntry from);

    // this method assumes it is ableToTransformByMethodFrom(from) returns true
    boolean needTransformMethodFrom(TypeEntry from);
}
