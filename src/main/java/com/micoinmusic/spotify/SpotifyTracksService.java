package com.micoinmusic.spotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.micoinmusic.domain.Track;
import com.micoinmusic.domain.dependencies.TracksService;
import com.micoinmusic.spotify.parsers.SpotifyJsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SpotifyTracksService implements TracksService {

    private static final String TRACKS = "v1/tracks?ids=<tracksId>&market=US";

    @Autowired
    private SpotifyJsonParser spotifyJsonParser;

    @Autowired
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyTracksService(String baseUrl) {
        this.spotifyJsonParser = new SpotifyJsonParser();
        this.spotifyHttpClient = new SpotifyHttpClient(baseUrl);
    }

    @Override
    public List<Track> getTracksWithPopularityInformation(String authToken, List<String> tracksId) {
        String url = TRACKS.replace("<tracksId>", tracksId.stream().collect(Collectors.joining(",")));
        String requestResponse = spotifyHttpClient.doCall(url, authToken);
        Function<JsonObject, JsonArray> elementsToGet = jsonObject -> jsonObject.getAsJsonArray("tracks");

        return spotifyJsonParser.getElementsFrom(requestResponse, Track.class, elementsToGet);
    }
}
