package com.micoinmusic.spotify.parsers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.micoinmusic.domain.Album;
import com.micoinmusic.spotify.deserializers.AlbumDeserializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

@Component
public class SpotifyJsonParser {

    private Gson gson;
    private JsonParser parser;

    public SpotifyJsonParser() {
        parser = new JsonParser();
        gson = createGsonWithConfiguration();
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

    private Gson createGsonWithConfiguration() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Album.class, new AlbumDeserializer());
        gsonBuilder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES);

        return gsonBuilder.create();
    }
}
