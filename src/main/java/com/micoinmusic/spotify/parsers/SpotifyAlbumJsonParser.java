package com.micoinmusic.spotify.parsers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class SpotifyAlbumJsonParser {

    private JsonObject json;

    public String getName() {
        return json.get("name").getAsString();
    }

    public String getId() {
        return json.get("id").getAsString();
    }

    public LocalDate getReleaseDate() {
        JsonElement releaseDateElement = json.get("release_date");
        LocalDate releaseDate = releaseDateElement != null ? LocalDate.parse(releaseDateElement.getAsString()) : null;

        return releaseDate;
    }

    public String getArtist() {
        return json.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
    }
}
