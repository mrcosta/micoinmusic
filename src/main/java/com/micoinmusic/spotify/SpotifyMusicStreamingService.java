package com.micoinmusic.spotify;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.micoinmusic.domain.Artist;
import com.micoinmusic.domain.dependencies.MusicStreamingService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SpotifyMusicStreamingService implements MusicStreamingService {

    private static final String FOLLOWED_ARTISTS_ENDPOINT = "v1/me/following?type=artist&limit=50";

    private Gson gson;
    private JsonParser parser;
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyMusicStreamingService(SpotifyHttpClient spotifyHttpClient) {
        this.gson = new Gson();
        this.parser = new JsonParser();
        this.spotifyHttpClient = spotifyHttpClient;
    }

    @Override
    public List<Artist> getFollowedArtists() {
        JsonElement nextCursor = null;
        List<Artist> artists = new ArrayList<>();

        while (isNotTheLastRequest(nextCursor)) {
            String requestResponse = requestFollowedArtists(nextCursor);
            artists.addAll(getArtistsFromResponse(requestResponse));

            nextCursor = getNextCursor(requestResponse);
        }

        return artists;
    }

    private JsonElement getNextCursor(String requestResponse) {
        return parser.parse(requestResponse).getAsJsonObject().getAsJsonObject("artists").getAsJsonObject("cursors").get("after");
    }

    private String requestFollowedArtists(JsonElement nextCursor) {
        String after = nextCursor == null ? "" : "&after=" + nextCursor.getAsString();

        return spotifyHttpClient.doCall(FOLLOWED_ARTISTS_ENDPOINT + after);
    }

    private List<Artist> getArtistsFromResponse(String response) {
        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
        JsonArray artistsInJson = jsonObject.getAsJsonObject("artists").getAsJsonArray("items");
        Type convertTo = new TypeToken<List<Artist>>(){}.getType();

        return gson.fromJson(artistsInJson, convertTo);
    }

    private boolean isNotTheLastRequest(JsonElement nextCursor) {
        return !JsonNull.class.isInstance(nextCursor);
    }
}
