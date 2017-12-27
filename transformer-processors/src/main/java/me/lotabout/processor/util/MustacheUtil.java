package me.lotabout.processor.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheException;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import me.lotabout.processor.TransformerProcessor;

public interface MustacheUtil {
    MustacheFactory FACTORY = new DefaultMustacheFactory();

    static Mustache loadTemplate(String resourceName) {
        try (Reader reader = new InputStreamReader(Resources.getResource(TransformerProcessor.class, resourceName)
                .openStream(), Charsets.UTF_8)) {
            return FACTORY.compile(reader, resourceName);
        } catch (IOException e) {
            return null;
        } catch (MustacheException ex) {
            // FIXME if we can use system.out or err, write out stack here and delete stack part form
            // TransformerProcessorMustacheException#getMessage
            Throwable realCause = ex.getCause() == null ? ex : ex.getCause();
            throw new TransformerProcessorMustacheException(ex.getMessage(), realCause);
        }
    }
}

