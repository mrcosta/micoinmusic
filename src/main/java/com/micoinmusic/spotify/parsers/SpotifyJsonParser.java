package com.micoinmusic.spotify.parsers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SpotifyJsonParser {

    public <T> List<T> getElementsFrom(String response, Class clazz, Function<JsonObject, JsonArray> getElements) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonArray elementsInJson = getElements.apply(jsonObject);
        Type convertTo = TypeToken.getParameterized(ArrayList.class, clazz).getType();

        return new Gson().fromJson(elementsInJson, convertTo);
    }

    public <T> T getSingleElementFrom(String response, Class clazz, Function<JsonObject, JsonElement> getElement) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonElement element = getElement.apply(jsonObject);
        Type convertTo = TypeToken.get(clazz).getType();

        return new Gson().fromJson(element, convertTo);
    }
}
