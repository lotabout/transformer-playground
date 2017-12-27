package me.lotabout.processor.model;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

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

    public String getName() {
        return self.getSimpleName().toString();
    }

    public static FieldModel of(VariableElement element) {
        return new FieldModel(element);
    }
}

