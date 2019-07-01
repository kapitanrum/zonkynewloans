package com.github.zonky.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

/**
 * Util methods for validation or transforming JSON
 */
public class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
        //Util class
    }

    /**
     * Validate JSON format
     *
     * @param jsonInput Input string
     * @return True if jsonInput is valid, otherwise false.
     */
    public static boolean isJsonValid(String jsonInput) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInput);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Transform JSON array string to object list.
     *
     * @param jsonInput JSON string
     * @param clazz     Bean class
     * @return List of beans.
     */
    public static <T> List<T> transformJsonArray(String jsonInput, Class<T> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.setDateFormat(new StdDateFormat().withLenient(true).withColonInTimeZone(true));
        List<T> result = null;
        try {
            result = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            LOG.error("Cannot read JSON: ", e);
        }
        return result;
    }

    /**
     * Pretty print for JSON object
     *
     * @param object JSON object
     * @return Formatted JSON string
     */
    public static String prettyPrint(Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.error("Cannot print JSON object", e);
        }
        return null;
    }
}
