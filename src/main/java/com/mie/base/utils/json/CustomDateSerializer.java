package com.mie.base.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WangRicky on 2018/5/16.
 */
public class CustomDateSerializer extends JsonSerializer<Date> {
    public CustomDateSerializer() {
    }

    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = "";
        if (date != null) {
            dateStr = format.format(date);
        }

        jsonGenerator.writeString(dateStr);
    }
}
