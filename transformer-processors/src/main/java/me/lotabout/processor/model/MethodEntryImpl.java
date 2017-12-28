package me.lotabout.processor.model;

import javax.lang.model.element.ExecutableElement;

public class MethodEntryImpl implements MethodEntry {
    private ExecutableElement self;

    public MethodEntryImpl(ExecutableElement self) {
        this.self = self;
    }
}
