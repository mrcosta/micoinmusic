package com.micoinmusic.spotify;

import com.micoinmusic.domain.Album;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpotifyArtistsServiceTest extends HttpBuildResponses {

    private SpotifyArtistsService spotifyArtistsService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        spotifyArtistsService = new SpotifyArtistsService(server.url("").toString());
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void shouldGetAllTheAlbumsForTheGivenArtist() throws Exception {
        addResponse("requests_stubs/artists/artist_albums.json");

        List<Album> albums = spotifyArtistsService.getAlbums("AQDxVIjCisbrCzM", "12Chz98pHFMPJEknJQMWvI");

        assertThat(albums.size(), is(10));
        assertThat(server.takeRequest().getPath(), is("/v1/artists/12Chz98pHFMPJEknJQMWvI/albums?album_type=album&market=US&limit=50"));
    }

    @Test
    public void shouldReturnEmptyListWhenArtistHasNoAlbumsInSpotify() throws Exception {
        addResponse("requests_stubs/artists/empty_artist_albums.json");

        List<Album> albums = spotifyArtistsService.getAlbums("AQDxVIjCisbrCzM", "4BclNkZtAUq1YrYNzye3N7");

        assertThat(albums.size(), is(0));
        assertThat(server.takeRequest().getPath(), is("/v1/artists/4BclNkZtAUq1YrYNzye3N7/albums?album_type=album&market=US&limit=50"));
    }
}