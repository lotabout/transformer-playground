package me.lotabout.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;

import me.lotabout.annotation.Transformer;
import me.lotabout.processor.model.EntryFactory;
import me.lotabout.processor.model.FieldEntry;
import me.lotabout.processor.model.TransformerMethod;
import me.lotabout.processor.model.TypeEntry;

@SupportedAnnotationTypes("me.lotabout.annotation.Transformer")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TransformerProcessor extends AbstractProcessor {
    private final String transformerTemplate = "template/TransformerTemplate.vm";

    public TransformerProcessor() {
        super();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        if (SourceVersion.latest().compareTo(SourceVersion.RELEASE_8) > 0) {
            return SourceVersion.latest();
        } else {
            return SourceVersion.RELEASE_8;
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(Transformer.class.getCanonicalName());
    }

    public void debug(Element e, String message, Object... objects) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(message, objects), e);
    }

    public void error(Element e, String message, Object... objects) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(message, objects), e);
    }

    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Transformer.class)
                .stream()
                .filter(TypeElement.class::isInstance)
                .map(TypeElement.class::cast)
                .forEach(this::generateTransformer);
        return true;
    }

    private void generateTransformer(TypeElement orig) {
        TypeEntry clazz = EntryFactory.of(orig.asType());
        List<TypeEntry> fromTypes = TypeEntry.getTransformerClasses(clazz, "from");
        List<TypeEntry> toTypes = TypeEntry.getTransformerClasses(clazz, "to");

        // generate method for each type
        List<TransformerMethod> transformerMethods = new ArrayList<>();

        fromTypes.forEach(c -> transformerMethods.add(TransformerMethod.of(c, clazz, "from"+c.getName())));
        toTypes.forEach(c -> transformerMethods.add(TransformerMethod.of(clazz, c, "to"+c.getName())));

        Set<String> imports = new HashSet<>();
        transformerMethods.forEach(m -> imports.addAll(m.getAllImports()));
        List<String> importList = new ArrayList<>(imports);
        Collections.sort(importList);

        // write contents
        VelocityContext context = new VelocityContext();
        context.put("clazz", clazz);
        context.put("transformers", transformerMethods);
        context.put("importList", importList);
        context.put("processor", this);
        outputSourceCode(transformerTemplate, context, clazz.getName() + "Transformer", orig);
    }

    private void outputSourceCode(String templateName, VelocityContext context, String sourceFileName, TypeElement e) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        final Filer filer = processingEnv.getFiler();
        try (OutputStream outputStream = filer.createSourceFile(sourceFileName, e).openOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outputStream, Charsets.UTF_8);
                PrintWriter writer = new PrintWriter(osw)) {
            Template template = velocityEngine.getTemplate(templateName);
            template.merge(context, writer);
        } catch (IOException ex) {
            throw new TransformerProcessingException(e, "could not output processing result to file '"
                    + sourceFileName
                    + "'.", ex);
        }
    }
}
