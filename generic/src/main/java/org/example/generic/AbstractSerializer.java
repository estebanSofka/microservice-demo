package org.example.generic;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;


public abstract class AbstractSerializer {


    protected Gson gson;


    protected AbstractSerializer() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(Instant.class, new LocalDateTimeDeserializer())
                .serializeNulls()
                .create();
    }


    public Gson getGson() {
        return gson;
    }

    private static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime source, Type typeOfSource, JsonSerializationContext context) {
            return new JsonPrimitive(source.toString());
        }
    }

    private static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfTarget, JsonDeserializationContext context) {
            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
        }
    }
}