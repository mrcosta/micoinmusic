package com.micoinmusic.spotify;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.micoinmusic.domain.Album;
import com.micoinmusic.domain.Artist;
import com.micoinmusic.domain.dependencies.ArtistsService;

import java.util.List;

public class SpotifyArtistsService implements ArtistsService {

    private static final String ARTISTS_ALBUNS = "v1/me/following?type=artist&limit=50";

    private Gson gson;
    private JsonParser parser;
    private SpotifyHttpClient spotifyHttpClient;

    public SpotifyArtistsService(String baseUrl) {
        this.gson = new Gson();
        this.parser = new JsonParser();
        this.spotifyHttpClient = new SpotifyHttpClient(baseUrl);
    }

    @Override
    public List<Album> getAlbums(String authToken, Artist artist) {
        return null;
    }
}
