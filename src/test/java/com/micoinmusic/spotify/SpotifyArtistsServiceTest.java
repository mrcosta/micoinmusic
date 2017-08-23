package com.micoinmusic.spotify;

import com.micoinmusic.domain.Album;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
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
        List<String> albumsNames = albums.stream().map(Album::getName).collect(toList());

        assertThat(albums.size(), is(10));
        assertThat(albumsNames.get(0), is("Drones"));
        assertThat(albumsNames.get(9), is("Showbiz"));
        assertThat(server.takeRequest().getPath(), is("/v1/artists/12Chz98pHFMPJEknJQMWvI/albums?album_type=album&market=US&limit=50"));
    }

    @Test
    public void shouldReturnEmptyListWhenArtistHasNoAlbumsInSpotify() throws Exception {
        addResponse("requests_stubs/artists/empty_artist_albums.json");

        List<Album> albums = spotifyArtistsService.getAlbums("AQDxVIjCisbrCzM", "4BclNkZtAUq1YrYNzye3N7");

        assertThat(albums.size(), is(0));
        assertThat(server.takeRequest().getPath(), is("/v1/artists/4BclNkZtAUq1YrYNzye3N7/albums?album_type=album&market=US&limit=50"));
    }

    @Test
    public void shouldGetTheAlbumsForTheGivenArtistWithReleaseDate() throws Exception {
        addResponse("requests_stubs/artists/artist_albums_with_release_date.json");

        List<Album> albums = spotifyArtistsService.getAlbumsWithReleaseDate("AQDxVIjCisbrCzM", Arrays.asList("2wart5Qjnvx1fd7LPdQxgJ", "2m7L60M210ABzrY9GLyBPZ", "3KuXEGcqLcnEYWnn3OEGy0"));
        List<LocalDate> albumsReleaseDates = albums.stream().map(Album::getReleaseDate).collect(toList());

        assertThat(albums.size(), is(3));
        assertThat(albumsReleaseDates.get(0).getYear(), is(2015));
        assertThat(albumsReleaseDates.get(2).getYear(), is(2012));
        assertThat(server.takeRequest().getPath(), is("/v1/albums?ids=2wart5Qjnvx1fd7LPdQxgJ,2m7L60M210ABzrY9GLyBPZ,3KuXEGcqLcnEYWnn3OEGy0&market=US"));
    }
}