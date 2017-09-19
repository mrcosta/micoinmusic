package com.micoinmusic.spotify.parsers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.micoinmusic.domain.Album;
import com.micoinmusic.domain.Track;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static java.util.stream.Collectors.toList;

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

        JsonDeserializer<Album> trackDeserializer = (jsonElement, typeOfT, context) -> {
            JsonObject json = jsonElement.getAsJsonObject();
            String name = json.get("name").getAsString();
            String id = json.get("id").getAsString();
            String artist = json.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();

            JsonElement releaseDateElement = json.get("release_date");
            LocalDate releaseDate = releaseDateElement != null ? LocalDate.parse(releaseDateElement.getAsString()) : null;

            if (json.has("tracks")) {
                Function<JsonObject, JsonArray> elementsToGet = jsonObject -> jsonObject.getAsJsonObject("tracks").getAsJsonArray("items");
                List<Track> tracksWithoutArtistAndAlbum = getElementsFrom(jsonElement.toString(), Track.class, elementsToGet);

                return new Album(name, id, releaseDate, tracksWithoutArtistAndAlbum.stream().map(track -> new Track(track.getName(), track.getId(), artist, name)).collect(toList()));
            }

            return new Album(name, id, releaseDate, null);
        };

        gsonBuilder.registerTypeAdapter(Album.class, trackDeserializer);
        gsonBuilder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES);

        return gsonBuilder.create();
    }
}
