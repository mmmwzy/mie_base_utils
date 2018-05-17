package com.mie.base.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mie.base.utils.SpringContextHolder;

import java.io.IOException;

/**
 * Created by WangRicky on 2018/5/16.
 */
public class JsonUtils {
    private static final String lock = "lock";
    private static ObjectMapper objectMapper = null;

    public JsonUtils() {
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper != null) {
            return objectMapper;
        } else {
            String var0 = "lock";
            synchronized("lock") {
                if (objectMapper != null) {
                    return objectMapper;
                }

                objectMapper = SpringContextHolder.getOneBean(ObjectMapper.class) == null ? new ObjectMapper() : (ObjectMapper)SpringContextHolder.getOneBean(ObjectMapper.class);
            }

            return objectMapper;
        }
    }

    public static String writeValueAsString(Object object) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(object);
    }

    public static <T> T convertValue(String fromValue, Class<T> toValueType) throws JsonParseException, JsonMappingException, IOException {
        getObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return getObjectMapper().readValue(fromValue, toValueType);
    }

    public static <T> T convertCollection(String fromValue, Class<T> collectionClass, Class<?> subCollectionClass, Class... elementClasses) throws JsonParseException, JsonMappingException, IOException {
        JavaType javaType = getObjectMapper().getTypeFactory().constructParametrizedType(collectionClass, subCollectionClass, elementClasses);
        return getObjectMapper().readValue(fromValue, javaType);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?> subCollectionClass, Class... elementClasses) {
        return getObjectMapper().getTypeFactory().constructParametrizedType(collectionClass, subCollectionClass, elementClasses);
    }
}

