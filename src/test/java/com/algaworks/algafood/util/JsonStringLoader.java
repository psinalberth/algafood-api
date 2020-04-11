package com.algaworks.algafood.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonStringLoader {

    public static String loadContent(String resource) {
        try {
            InputStream stream = JsonStringLoader.class.getResourceAsStream(resource);
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}