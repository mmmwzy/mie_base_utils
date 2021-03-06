package com.mie.base.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WangRicky on 2018/5/16.
 */
public class CustomDateTimeSerializer extends JsonSerializer<Date> {
    public CustomDateTimeSerializer() {
    }

    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "";
        if (date != null) {
            dateStr = format.format(date);
        }

        jsonGenerator.writeString(dateStr);
    }
}
