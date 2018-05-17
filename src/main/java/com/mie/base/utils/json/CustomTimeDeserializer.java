package com.mie.base.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by WangRicky on 2018/5/16.
 */

public class CustomTimeDeserializer extends JsonDeserializer<Date> {
    public CustomTimeDeserializer() {
    }

    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        try {
            String dateTimeStr = parser.getValueAsString();
            return DateUtils.parseDate(dateTimeStr, new String[]{"HH:mm:ss"});
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
