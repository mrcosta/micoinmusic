package com.micoinmusic.domain;

import com.micoinmusic.spotify.SpotifyArtistsService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AlbumsTest {

    private final static String AUTH_TOKEN = "R4nd0M4UthToken";

    private Albums albums;
    private SpotifyArtistsService spotifyArtistsService;

    @Before
    public void setUp() {
        spotifyArtistsService = mock(SpotifyArtistsService.class);
        albums = new Albums(spotifyArtistsService);
    }

    @Test
    public void shouldGetTheArtistAlbumForThisCurrentYear() {
        List<Album> arcadeFirealbums = asList(new Album("Everything Now", "idEN"), new Album("Reflektor (Deluxe)", "idRD"), new Album("Reflektor", "idR"));
        when(spotifyArtistsService.getAlbums(AUTH_TOKEN, "arcadeFireId")).thenReturn(arcadeFirealbums);

        Tracks everythingNowtracks = new Tracks(asList(new Track("Everything_Now(continued)", "everythingNowContinuedId"), new Track("Everything Now", "everythingNowId")));
        List<Album> arcadeFirealbumsWithReleaseDate = asList(new Album("Everything Now", "idEN", LocalDate.now(), everythingNowtracks), new Album("Reflektor (Deluxe)", "idRD", LocalDate.of(2015, 7, 2)), new Album("Reflektor", "idR", LocalDate.of(2013, 7, 2)));
        when(spotifyArtistsService.getAlbumsWithReleaseDate(AUTH_TOKEN, asList("idEN", "idRD", "idR"))).thenReturn(arcadeFirealbumsWithReleaseDate);

        Album album = albums.getArtistAlbumFromCurrentYear(AUTH_TOKEN, "arcadeFireId");
        List<Track> tracks = album.getTracks().getItems();

        assertThat(album.getName(), is("Everything Now"));
        assertThat(album.getReleaseDate().getYear(), is(LocalDate.now().getYear()));
        assertThat(tracks.get(0).getName(), is("Everything_Now(continued)"));
        assertThat(tracks.get(1).getName(), is("Everything Now"));
    }

    @Test
    public void shouldReturnNullWhenThereIsNoAlbum() {
        List<Album> arcadeFirealbums = asList(new Album("Everything Now", "idEN"), new Album("Reflektor (Deluxe)", "idRD"), new Album("Reflektor", "idR"));
        when(spotifyArtistsService.getAlbums(AUTH_TOKEN, "arcadeFireId")).thenReturn(arcadeFirealbums);

        List<Album> arcadeFirealbumsWithReleaseDate = asList(new Album("Reflektor (Deluxe)", "idRD", LocalDate.of(2015, 7, 2)), new Album("Reflektor", "idR", LocalDate.of(2013, 7, 2)));
        when(spotifyArtistsService.getAlbumsWithReleaseDate(AUTH_TOKEN, asList("idRD", "idR"))).thenReturn(arcadeFirealbumsWithReleaseDate);

        Album album = albums.getArtistAlbumFromCurrentYear(AUTH_TOKEN, "arcadeFireId");

        assertThat(album, is(nullValue()));
    }
}