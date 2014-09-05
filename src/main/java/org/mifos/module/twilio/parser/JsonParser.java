package org.mifos.module.twilio.parser;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    private final Gson gson = new Gson();

    public JsonParser() {
        super();
    }

    public <T> T parse(final String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }
}
