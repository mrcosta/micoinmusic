package com.micoinmusic.spotify;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.micoinmusic.domain.Artist;
import com.micoinmusic.domain.dependencies.MusicStreamingService;

import java.lang.reflect.Type;
import java.util.List;

public class SpotifyMusicStreamingService implements MusicStreamingService {

    private static final String FOLLOWED_ARTISTS_ENDPOINT = "/v1/me/following?type=artist&limit=50";

    private Gson gson;
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyMusicStreamingService(SpotifyHttpClient spotifyHttpClient) {
        this.gson = new Gson();
        this.spotifyHttpClient = spotifyHttpClient;
    }

    @Override
    public List<Artist> getFollowedArtists() {
        Type convertTo = new TypeToken<List<Artist>>(){}.getType();
        JsonObject jsonObject = new JsonParser().parse(spotifyHttpClient.doCall(FOLLOWED_ARTISTS_ENDPOINT)).getAsJsonObject();
        JsonArray artists = jsonObject.getAsJsonObject("artists").getAsJsonArray("items");

        return gson.fromJson(artists, convertTo);
    }
}
