package me.lotabout.processor.model;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class MethodModel {
    private ExecutableElement self;

    private MethodModel(ExecutableElement self) {
        this.self = self;
    }

    public ExecutableElement getRaw() {
        return self;
    }

    public static MethodModel of(ExecutableElement element) {
        return new MethodModel(element);
    }
}
