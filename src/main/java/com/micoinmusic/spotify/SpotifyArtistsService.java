package com.micoinmusic.spotify;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.micoinmusic.domain.Album;
import com.micoinmusic.domain.dependencies.ArtistsService;

import java.lang.reflect.Type;
import java.util.List;

public class SpotifyArtistsService implements ArtistsService {

    private static final String ARTISTS_ALBUNS = "v1/artists/<artistId>/albums?album_type=album&market=US&limit=50";

    private Gson gson;
    private JsonParser parser;
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyArtistsService(String baseUrl) {
        this.gson = new Gson();
        this.parser = new JsonParser();
        this.spotifyHttpClient = new SpotifyHttpClient(baseUrl);
    }

    @Override
    public List<Album> getAlbums(String authToken, String albumId) {
        String url = ARTISTS_ALBUNS.replace("<artistId>", albumId);
        String requestResponse = spotifyHttpClient.doCall(url, authToken);

        return getAlbumsFromResponse(requestResponse);
    }

    private List<Album> getAlbumsFromResponse(String response) {
        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
        JsonArray artistsInJson = jsonObject.getAsJsonArray("items");
        Type convertTo = new TypeToken<List<Album>>(){}.getType();

        return gson.fromJson(artistsInJson, convertTo);
    }
}
