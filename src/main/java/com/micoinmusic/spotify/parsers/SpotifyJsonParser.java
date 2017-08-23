package com.micoinmusic.spotify.parsers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SpotifyJsonParser {

    private Gson gson;
    private JsonParser parser;

    public SpotifyJsonParser() {
        parser = new JsonParser();

        JsonDeserializer<LocalDate> localDateDeserializer = (json, typeOfT, context) -> DateTimeFormatter.ISO_LOCAL_DATE.parse(json.getAsString(), LocalDate::from);
        gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, localDateDeserializer).create();
    }

    public <T> List<T> getElementsFrom(String response, Class clazz, Function<JsonObject, JsonArray> getElements) {
        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
        JsonArray elementsInJson = getElements.apply(jsonObject);
        Type convertTo = TypeToken.getParameterized(ArrayList.class, clazz).getType();


        return gson.fromJson(elementsInJson, convertTo);
    }

    public <T> T getSingleElementFrom(String response, Class clazz, Function<JsonObject, JsonElement> getElement) {
        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
        JsonElement element = getElement.apply(jsonObject);
        Type convertTo = TypeToken.get(clazz).getType();

        return gson.fromJson(element, convertTo);
    }
}
