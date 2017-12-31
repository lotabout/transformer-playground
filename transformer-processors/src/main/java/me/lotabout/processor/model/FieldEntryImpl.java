package me.lotabout.processor.model;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;

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

        // we need to call the transformTo method of type to recursively generate the transformer
        // ex 1: to.setField(ATransformer.toB(from.getField()))
        // ex 2: to.setField(BTransformer.fromA(from.getField()))
        // ex 3: to.setField(from.getField().stream(l1 -> l1).collect(Collectors.toList()))
        // ...

        // to.setField(xxxx(from.getField())xxx)
        String value = String.format("from.%s()", getGetter());
        List<Object> params = new ArrayList<>();

        // perform transformation
        String transformer = this.getType().transformTo(to.getType(), value, params);

        String format = String.format("to.%s(%s)", this.getSetter(), transformer);
        builder.addStatement(format, params.toArray());
    }

    @Override
    public String getGetter() {
        String prefix = this.getType().isBoolean() ? "is" : "get";
        return prefix + this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1);
    }
}
