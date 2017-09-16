package com.micoinmusic.spotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.micoinmusic.domain.Album;
import com.micoinmusic.domain.dependencies.AlbumsService;
import com.micoinmusic.spotify.parsers.SpotifyJsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class SpotifyAlbumsService implements AlbumsService {

    private static final String ARTIST_ID_PARAMETER = "<artistId>";
    private static final String ARTISTS_ALBUNS = "v1/artists/" + ARTIST_ID_PARAMETER + "/albums?album_type=album&market=US&limit=50";
    private static final String ALBUMS_ID_PARAMETER = "<albumId>";
    private static final String ALBUMS = "v1/albums?ids=" + ALBUMS_ID_PARAMETER +"&market=US";

    private SpotifyJsonParser spotifyJsonParser;
    private SpotifyHttpClient spotifyHttpClient;

    @Autowired
    public SpotifyAlbumsService(SpotifyJsonParser spotifyJsonParser, SpotifyHttpClient spotifyHttpClient) {
        this.spotifyJsonParser = spotifyJsonParser;
        this.spotifyHttpClient = spotifyHttpClient;
    }

    @Override
    public List<Album> getAlbums(String authToken, String artistId) {
        String url = ARTISTS_ALBUNS.replace(ARTIST_ID_PARAMETER, artistId);
        return getAlbumsFrom(authToken, url, "items");
    }

    @Override
    public Album getLastAlbumReleaseDate(String authToken, String albumId) {
        String url = ALBUMS.replace(ALBUMS_ID_PARAMETER, albumId);
        return getAlbumsFrom(authToken, url, "albums").get(0);
    }

    private List<Album> getAlbumsFrom(String authToken, String url, String startingObject) {
        String requestResponse = spotifyHttpClient.doCall(url, authToken);
        Function<JsonObject, JsonArray> elementsToGet = jsonObject -> jsonObject.getAsJsonArray(startingObject);

        return spotifyJsonParser.getElementsFrom(requestResponse, Album.class, elementsToGet);
    }
}
