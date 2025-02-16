package es.dpm.util;

import es.dpm.modelo.Comida;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author danielpm.dev
 */
@Converter
public class ComidaListConverter implements AttributeConverter<List<Comida>, String> {

    private static final String SEPARADOR = ", "; // Una , seguida de un espacio

    // Convierte de Lista a String (para almacenar en la BD)
    @Override
    public String convertToDatabaseColumn(List<Comida> comidas) {
        if (comidas == null || comidas.isEmpty()) {
            return null;
        }
        return comidas.stream().map(Enum::name).collect(Collectors.joining(SEPARADOR));
    }

    // Convierte de String a Lista (para recuperar desde la BD)
    @Override
    public List<Comida> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(SEPARADOR))
                .map(Comida::valueOf)
                .collect(Collectors.toList());
    }
}
