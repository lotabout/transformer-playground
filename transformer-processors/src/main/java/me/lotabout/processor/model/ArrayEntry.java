package me.lotabout.processor.model;

import com.squareup.javapoet.ClassName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import java.util.*;

// No plan to support Array
public class ArrayEntry implements TypeEntry {
    private ArrayType self;

    public ArrayEntry(TypeMirror self) {
        this.self = (ArrayType)self;
    }

    @Override
    public String getQualifiedName() {
        return self.toString();
    }

    @Override
    public String getName() {
        return self.toString();
    }

    @Override
    public String getFullName() {
        TypeEntry componentClass = EntryFactory.of(self.getComponentType());
        return componentClass.getFullName() + "[]";
    }

    @Override
    public String getPackageName() {
        return "";
    }

    @Override
    public List<FieldEntry> getAllFields() {
        return Collections.emptyList();
    }

    @Override
    public List<MethodEntry> getAllMethods() {
        return Collections.emptyList();
    }

    @Override
    public TypeMirror getRaw() {
        return self;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean ableToTransformDirectlyTo(TypeEntry to) {
        return to instanceof ArrayEntry
                && this.getComponentType().ableToTransformDirectlyTo(((ArrayEntry) to).getComponentType());
    }

    @Override
    public boolean ableToTransformByTransformerTo(TypeEntry to) {
        return to instanceof ArrayEntry
                && this.getComponentType().ableToTransformByTransformerTo(((ArrayEntry) to).getComponentType());
    }

    @Override
    public Optional<AnnotationMirror> getAnnotationMirror(Class<?> clazz) {
        return Optional.empty();
    }

    @Override
    public String transformTo(TypeEntry to, String value, List<Object> params, int level) {
        final String ret;

        TypeEntry componentOfA = this.getComponentType();
        TypeEntry componentOfB = ((ArrayEntry)to).getComponentType();

        List<Object> innerParams = new ArrayList<>();
        String listVar = "l" + String.valueOf(level);
        String transformer = componentOfA.transformTo(componentOfB, listVar, innerParams, level + 1);

        params.add(0, ClassName.get(Arrays.class));
        params.addAll(innerParams);

        if (componentOfB.isPrimitive()) {
            ret = String.format("$T.stream(%s).map(%s -> %s).toArray()", value, listVar, transformer);
        } else {
            ret = String.format("$T.stream(%s).map(%s -> %s).toArray($T::new)", value, listVar, transformer);
            params.add(ClassName.get(this.getRaw()));
        }

        return ret;
    }

    public TypeEntry getComponentType() {
        return EntryFactory.of(self.getComponentType());
    }
}
