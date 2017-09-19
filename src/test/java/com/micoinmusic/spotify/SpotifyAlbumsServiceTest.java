package com.micoinmusic.spotify;

import com.micoinmusic.domain.Album;
import com.micoinmusic.spotify.parsers.SpotifyJsonParser;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpotifyAlbumsServiceTest extends HttpBuildResponses {

    private SpotifyAlbumsService spotifyArtistsService;

    @BeforeAll
    public void setUp() throws Exception {
        super.setUp();
        spotifyArtistsService = new SpotifyAlbumsService(new SpotifyJsonParser(), new SpotifyHttpClient(server.url("").toString()));
    }

    @AfterAll
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

        Album lastAlbumWithReleaseDate = spotifyArtistsService.getLatestAlbumReleaseDate("AQDxVIjCisbrCzM", "2wart5Qjnvx1fd7LPdQxgJ");

        assertThat(lastAlbumWithReleaseDate.getReleaseDate().getYear(), is(2015));
        assertThat(lastAlbumWithReleaseDate.getTracks().get(0).getName(), is("Dead Inside"));
        assertThat(lastAlbumWithReleaseDate.getTracks().get(0).getId(), is("2daZovie6pc2ZK7StayD1K"));
        assertThat(lastAlbumWithReleaseDate.getTracks().get(0).getAlbum(), is("Drones"));
        assertThat(lastAlbumWithReleaseDate.getTracks().get(0).getArtist(), is("Muse"));
        assertThat(server.takeRequest().getPath(), is("/v1/albums?ids=2wart5Qjnvx1fd7LPdQxgJ&market=US"));
    }
}