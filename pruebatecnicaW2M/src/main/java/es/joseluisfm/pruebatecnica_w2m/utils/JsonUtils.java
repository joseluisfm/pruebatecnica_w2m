package es.joseluisfm.pruebatecnica_w2m.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Convierte un JSON String a un objeto del tipo especificado.
     *
     * @param jsonString El JSON en formato String.
     * @param clazz      La clase del tipo de objeto al que deseas convertir el JSON.
     * @param <T>        El tipo genérico del objeto.
     * @return Un objeto del tipo especificado, o null si el JSON está vacío o no válido.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     * @throws IOException Si ocurre un error de lectura del JSON.
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        return mapper.readValue(jsonString, clazz);
    }

    /**
     * Convierte un objeto en un JSON String.
     *
     * @param obj El objeto a convertir a JSON.
     * @return Un JSON en formato String.
     * @throws IOException Si ocurre un error de escritura del JSON.
     */
    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }
}
