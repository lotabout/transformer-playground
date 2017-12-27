package me.lotabout.processor.model;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.stream.Collectors;

public class ClassModel {
    private TypeElement self;

    public ClassModel(TypeElement self) {
        this.self = self;
    }

    public List<FieldModel> getAllFields() {
        return self.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.FIELD)
                .map(VariableElement.class::cast)
                .map(FieldModel::of)
                .collect(Collectors.toList());
    }

    public List<MethodModel> getAllMethods() {
        return self.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.METHOD)
                .map(ExecutableElement.class::cast)
                .map(MethodModel::of)
                .collect(Collectors.toList());
    }

    public TypeElement getRaw() {
        return self;
    }

    public boolean isCollection() {
//        self.getTypeParameters();
        return false;
    }

    public static ClassModel of(TypeElement element) {
        return new ClassModel(element);
    }
}
