package com.algaworks.algafood.infrastructure.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class CustomPageSerializer extends JsonSerializer<Page<?>> {
    @Override
    public void serialize(Page<?> page, JsonGenerator generator,
                          SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeObjectField("content", page.getContent());
        generator.writeNumberField("pageSize", page.getSize());
        generator.writeNumberField("totalElements", page.getTotalElements());
        generator.writeNumberField("pageNumber", page.getNumber());
        generator.writeNumberField("totalPages", page.getTotalPages());
        generator.writeEndObject();
    }
}
