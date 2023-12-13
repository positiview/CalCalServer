package com.example.handler;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class CustomDateTimeConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute != null ? attribute.format(formatter) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return dbData != null ? LocalDateTime.parse(dbData, formatter) : null;
    }
}