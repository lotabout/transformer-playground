package me.lotabout.processor;

import me.lotabout.annotation.Transformer;
import me.lotabout.processor.util.MustacheUtil;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.mustachejava.Mustache;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

@SupportedAnnotationTypes("me.lotabout.annotation.Transformer")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TransformerProcessor extends AbstractProcessor {
    private final Mustache transformerTemplate;

    public TransformerProcessor() {
        super();
        this.transformerTemplate = MustacheUtil.loadTemplate("TransformerTemplate.mustache");
        if (this.transformerTemplate == null) {
            final Messager messager = processingEnv.getMessager();
            messager.printMessage(Diagnostic.Kind.WARNING,
                    "disabled AutoIndexProcessor due to template loading problem.");
        }
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

    private void debug(String message, Element e, Object... objects) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(message, objects), e);
    }

    private void error(String message, Element e, Object... objects) {
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

    private void generateTransformer(TypeElement e) {
        Elements elementUtils = processingEnv.getElementUtils();
        Transformer transformer = e.getAnnotation(Transformer.class);
//        List<TypeElement> fromTypeList = Arrays.stream(transformer.from())
//                .map(Class::getCanonicalName)
//                .map(elementUtils::getTypeElement)
//                .collect(Collectors.toList());
//        List<TypeElement> toTypeList = Arrays.stream(transformer.from())
//                .map(Class::getCanonicalName)
//                .map(elementUtils::getTypeElement)
//                .collect(Collectors.toList());

        // generate method for each type


        String className = e.getQualifiedName() + "Transformer";

        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("ClassName", className);
        outputSourceCode(transformerTemplate, className, e, builder.build());
    }

    // generate method for fromType -> toType
    private void generateTransformerMethod(TypeElement from, TypeElement to) {
        Elements elementUtils = processingEnv.getElementUtils();

        // 1. collect the fieldName for each of the type
        List fieldsOfToTypye = elementUtils.getAllMembers(to);
    }

    private void outputSourceCode(Mustache template, String sourceFileName, TypeElement e, Object scope) {
        final Filer filer = processingEnv.getFiler();
        try (OutputStream outputStream = filer.createSourceFile(sourceFileName, e).openOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outputStream, Charsets.UTF_8);
                PrintWriter writer = new PrintWriter(osw)) {
            template.execute(writer, scope);
        } catch (IOException ex) {
            throw new TransformerProcessingException(e, "could not output processing result to file '"
                    + sourceFileName
                    + "'.", ex);
        }
    }

    private static String getPackageName(TypeElement e) {
        final PackageElement packageElement = (PackageElement)e.getEnclosingElement();
        return packageElement.isUnnamed() ? "" : packageElement.getQualifiedName().toString();
    }
}
