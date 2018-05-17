package com.mie.base.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by WangRicky on 2018/5/16.
 */
public class IntegerFromBooleanDeserializer extends JsonDeserializer<Integer> {
    public IntegerFromBooleanDeserializer() {
    }

    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        boolean value = p.getValueAsBoolean(false);
        return value ? Integer.valueOf(1) : Integer.valueOf(0);
    }
}
