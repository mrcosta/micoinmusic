package com.micoinmusic.spotify;

import com.google.gson.*;
import com.micoinmusic.domain.Artist;
import com.micoinmusic.domain.dependencies.ProfileService;
import com.micoinmusic.spotify.parsers.SpotifyJsonParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class SpotifyProfileService implements ProfileService {

    private static final String FOLLOWED_ARTISTS_ENDPOINT = "v1/me/following?type=artist&limit=50";
    private static final String FIRST_REQUEST = "FIRST_REQUEST";

    private SpotifyJsonParser spotifyJsonParser;
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyProfileService(String baseUrl) {
        this.spotifyJsonParser = new SpotifyJsonParser();
        this.spotifyHttpClient = new SpotifyHttpClient(baseUrl);
    }

    @Override
    public List<Artist> getFollowedArtists(String authToken) {
        String nextCursor = FIRST_REQUEST;
        List<Artist> artists = new ArrayList<>();

        while (isNotTheLastRequest(nextCursor)) {
            String requestResponse = requestFollowedArtists(nextCursor, authToken);
            artists.addAll(getArtistsFromResponse(requestResponse));

            nextCursor = getNextCursor(requestResponse);
        }

        return artists;
    }

    private String getNextCursor(String requestResponse) {
        Function<JsonObject, JsonElement> elementToGet = jsonObject -> jsonObject.getAsJsonObject("artists").getAsJsonObject("cursors").get("after");
        return spotifyJsonParser.getSingleElementFrom(requestResponse, String.class, elementToGet);
    }

    private String requestFollowedArtists(String nextCursor, String authToken) {
        String after = FIRST_REQUEST.equals(nextCursor) ? "" : "&after=" + nextCursor;

        return spotifyHttpClient.doCall(FOLLOWED_ARTISTS_ENDPOINT + after, authToken);
    }

    private List<Artist> getArtistsFromResponse(String response) {
        Function<JsonObject, JsonArray> elementsToGet = jsonObject -> jsonObject.getAsJsonObject("artists").getAsJsonArray("items");
        return spotifyJsonParser.getElementsFrom(response, Artist.class, elementsToGet);
    }

    private boolean isNotTheLastRequest(String nextCursor) {
        return nextCursor != null;
    }
}
