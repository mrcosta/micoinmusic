package com.micoinmusic.spotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.micoinmusic.domain.Album;
import com.micoinmusic.domain.dependencies.ArtistsService;
import com.micoinmusic.spotify.parsers.SpotifyJsonParser;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
public class SpotifyArtistsService implements ArtistsService {

    private static final String ARTISTS_ALBUNS = "v1/artists/<artistId>/albums?album_type=album&market=US&limit=50";

    @Autowired
    private SpotifyJsonParser spotifyJsonParser;

    @Autowired
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyArtistsService(String baseUrl) {
        this.spotifyJsonParser = new SpotifyJsonParser();
        this.spotifyHttpClient = new SpotifyHttpClient(baseUrl);
    }

//    public SpotifyArtistsService() {
//        this.spotifyJsonParser = new SpotifyJsonParser();
//        this.spotifyHttpClient = new SpotifyHttpClient();
//    }

    @Override
    public List<Album> getAlbums(String authToken, String albumId) {
        String url = ARTISTS_ALBUNS.replace("<artistId>", albumId);
        String requestResponse = spotifyHttpClient.doCall(url, authToken);
        Function<JsonObject, JsonArray> elementsToGet = jsonObject -> jsonObject.getAsJsonArray("items");

        return spotifyJsonParser.getElementsFrom(requestResponse, Album.class, elementsToGet);
    }
}
