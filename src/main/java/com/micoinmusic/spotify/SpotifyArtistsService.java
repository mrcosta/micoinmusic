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
import java.util.stream.Collectors;

@NoArgsConstructor
public class SpotifyArtistsService implements ArtistsService {

    private static final String ARTISTS_ALBUNS = "v1/artists/<artistId>/albums?album_type=album&market=US&limit=50";
    private static final String ALBUMS = "v1/albums?ids=<albumsId>&market=US";

    @Autowired
    private SpotifyJsonParser spotifyJsonParser;

    @Autowired
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyArtistsService(String baseUrl) {
        this.spotifyJsonParser = new SpotifyJsonParser();
        this.spotifyHttpClient = new SpotifyHttpClient(baseUrl);
    }

    @Override
    public List<Album> getAlbums(String authToken, String artistId) {
        String url = ARTISTS_ALBUNS.replace("<artistId>", artistId);
        return getAlbumsFrom(authToken, url, "items");
    }

    @Override
    public List<Album> getAlbumsWithReleaseDate(String authToken, List<String> albumsId) {
        String url = ALBUMS.replace("<albumsId>", albumsId.stream().collect(Collectors.joining(",")));
        return getAlbumsFrom(authToken, url, "albums");
    }

    private List<Album> getAlbumsFrom(String authToken, String url, String startingObject) {
        String requestResponse = spotifyHttpClient.doCall(url, authToken);
        Function<JsonObject, JsonArray> elementsToGet = jsonObject -> jsonObject.getAsJsonArray(startingObject);

        return spotifyJsonParser.getElementsFrom(requestResponse, Album.class, elementsToGet);
    }
}
