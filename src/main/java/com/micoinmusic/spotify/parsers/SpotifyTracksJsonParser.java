package com.micoinmusic.spotify.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.micoinmusic.domain.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class SpotifyTracksJsonParser extends SpotifyJsonParser {

    private JsonObject json;
    private SpotifyAlbumJsonParser albumParser;

    public SpotifyTracksJsonParser(JsonObject json) {
        this.json = json;
        albumParser = new SpotifyAlbumJsonParser(json);
    }

    public List<Track> getTracks() {
        List<Track> tracks = new ArrayList<>();

        if (json.has("tracks")) {
            Function<JsonObject, JsonArray> elementsToGet = jsonObject -> jsonObject.getAsJsonObject("tracks").getAsJsonArray("items");
            List<Track> tracksWithoutArtistAndAlbum = getElementsFrom(json.toString(), Track.class, elementsToGet);
            tracks = tracksWithoutArtistAndAlbum.stream().map(track -> new Track(track.getName(), track.getId(), albumParser.getArtist(), albumParser.getName())).collect(toList());
        }

        return tracks;
    }
}
