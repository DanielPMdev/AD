package com.dpm.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author danielpm.dev
 */
// Clase convertidora para List<String> a String y viceversa
@Converter
public class DiseaseListConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPARADOR = ", ";

    @Override
    public String convertToDatabaseColumn(List<String> diseases) {
        if (diseases == null || diseases.isEmpty()) {
            return null;
        }
        return diseases.stream().collect(Collectors.joining(SEPARADOR));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(SEPARADOR))
                .collect(Collectors.toList());
    }
}

