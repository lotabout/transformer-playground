package me.lotabout.processor.model;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

public class FieldEntryImpl implements FieldEntry {
    private VariableElement self;

    public FieldEntryImpl(VariableElement self) {
        this.self = self;
    }

    @Override
    public String getName() {
        return self.getSimpleName().toString();
    }

    @Override
    public TypeEntry getType() {
        return EntryFactory.of(self.asType());
    }

    @Override
    public String getSetter() {
        return "set" + this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1);
    }

    @Override
    public Element getElement() {
        return self;
    }

    @Override
    public void transformTo(FieldEntry to, TransformerClass env, MethodSpec.Builder builder) {
        if (!this.getName().equals(to.getName())) {
            return;
        }

        // check if type is transformable
        if (!this.getType().ableToTransformTo(to.getType())) {
            env.error(this.getElement(), "Unable to transform %s from %s -> %s. Try to add @Transformer annotation to the types or unify the type of the field.",
                    this.getName(), this.getType().getFullName(), to.getType().getFullName());
            return;
        }

        // perform transformation

    }

    @Override
    public String getGetter() {
        String prefix = this.getType().isBoolean() ? "is" : "get";
        return prefix + this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1);
    }
}
