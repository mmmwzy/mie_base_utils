package com.mie.base.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by WangRicky on 2018/5/16.
 */
public class IntegerToBooleanSerializer extends JsonSerializer<Integer> {
    public IntegerToBooleanSerializer() {
    }

    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        Integer yes = Integer.valueOf(1);
        if (yes.equals(value)) {
            gen.writeBoolean(true);
        } else {
            gen.writeBoolean(false);
        }

    }
}