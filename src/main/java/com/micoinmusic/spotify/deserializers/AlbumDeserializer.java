package com.micoinmusic.spotify.deserializers;

import com.google.gson.*;
import com.micoinmusic.domain.Album;
import com.micoinmusic.spotify.parsers.SpotifyAlbumJsonParser;
import com.micoinmusic.spotify.parsers.SpotifyTracksJsonParser;

import java.lang.reflect.Type;

public class AlbumDeserializer implements JsonDeserializer<Album> {

    private SpotifyAlbumJsonParser albumParser;
    private SpotifyTracksJsonParser tracksParser;

    @Override
    public Album deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        albumParser = new SpotifyAlbumJsonParser(jsonElement.getAsJsonObject());
        tracksParser = new SpotifyTracksJsonParser(jsonElement.getAsJsonObject());

        return new Album(albumParser.getName(), albumParser.getId(), albumParser.getReleaseDate(), tracksParser.getTracks());
    }
}
