package me.lotabout.processor.model;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;

public class FieldModel {
    private VariableElement self;

    private FieldModel(VariableElement self) {
        this.self = self;
    }

    public VariableElement getRaw() {
        return self;
    }

    public ClassModel getType() {
        return ClassModel.of((TypeElement)((DeclaredType)self.asType()).asElement());
    }

    public String getTypeName() {
        if (this.isPrimitive()) {
            return self.asType().toString();
        } else {
            return this.getType().getName();
        }
    }

    public String getName() {
        return self.getSimpleName().toString();
    }

    public static FieldModel of(VariableElement element) {
        return new FieldModel(element);
    }

    public String getSetter() {
        return "set" + this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1);
    }

    public String getGetter() {
        String prefix = this.isBoolean() ? "is" : "get";
        return prefix + this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1);
    }

    public String transformerMethod(FieldModel from) {
        // in case we need to use an internal method to transform two fields, we need a method name
        return from.getTypeName() + "2" + this.getTypeName();
    }

    public boolean isNeedTransform(FieldModel from) {
        if (this.isPrimitive()) {
            return self.asType().getKind() != from.getRaw().asType().getKind();
        } else if (this.getType().isCollection()) {
            // primitive & equal
            return true;
        } else {
            // same type or not
            return !this.getType().getQualifiedName().equals(from.getType().getQualifiedName());
        }
    }

    private boolean isPrimitive() {
        switch (self.asType().getKind()) {
        case BOOLEAN:
        case BYTE:
        case CHAR:
        case SHORT:
        case INT:
        case LONG:
        case FLOAT:
        case DOUBLE:
            return true;
        default:
            return false;
        }
    }

    private boolean isBoolean() {
        if (this.isPrimitive()) {
            return self.asType().getKind() == TypeKind.BOOLEAN;
        } else if (this.getType().getQualifiedName().equals("java.lang.Boolean")) {
            return true;
        } else {
            return false;
        }
    }

}

