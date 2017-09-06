package com.micoinmusic.domain;

import com.micoinmusic.spotify.SpotifyProfileService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlaylistTest {

    private final static String AUTH_TOKEN = "R4nd0M4UthToken";

    private Playlist playlist;

    private SpotifyProfileService spotifyProfileService;
    private Albums albums;

    @Before
    public void setUp() {
        spotifyProfileService = mock(SpotifyProfileService.class);
        albums = mock(Albums.class);

        playlist = new Playlist(spotifyProfileService, albums);
    }

    @Test
    public void shouldCreatePlaylistForTheGivenYear() throws Exception {
        when(spotifyProfileService.getFollowedArtists(AUTH_TOKEN)).thenReturn(asList(new Artist("Lorde", "1"), new Artist("Arcade Fire", "5"), new Artist("Foster the People", "9")));

        Tracks melodramaTracks = new Tracks(asList(new Track("Green Light", "glId", "Lorde", "Melodrama")));
        when(albums.getArtistAlbumFromCurrentYear(AUTH_TOKEN, "1")).thenReturn(new Album("Melodrama", melodramaTracks));

        when(albums.getArtistAlbumFromCurrentYear(AUTH_TOKEN, "5")).thenReturn(null);

        Tracks fosterTracks = new Tracks(asList(new Track("Sit Next to Me", "sntmId", "Foster The People", "Sacred Hearts Club")));
        when(albums.getArtistAlbumFromCurrentYear(AUTH_TOKEN, "9")).thenReturn(new Album("Sacred Hearts Club", fosterTracks));

        Playlist createdPlaylist = playlist.createPlaylist(AUTH_TOKEN);
        List<String> names = getPropertyList(createdPlaylist.getTracks(), Track::getName);
        List<String> artists = getPropertyList(createdPlaylist.getTracks(), Track::getArtist);
        List<String> albums = getPropertyList(createdPlaylist.getTracks(), Track::getAlbum);

        assertThat(createdPlaylist.getName(), is("This 2018 in music"));
        assertThat(createdPlaylist.getTracks().size(), is(2));
        assertThat(artists, is(asList("Lorde", "Foster The People")));
        assertThat(albums, is(asList("Melodrama", "Sacred Hearts Club")));
        assertThat(names, is(asList("Green Light", "Sit Next to Me")));
    }

    @Test
    public void shouldCreatePlaylistForTheGivenYearGettingOnlyTheFirst4TracksOfAnAlbum() throws Exception {
        when(spotifyProfileService.getFollowedArtists(AUTH_TOKEN)).thenReturn(asList(new Artist("Lorde", "1")));

        Tracks melodramaTracks = new Tracks(asList(new Track("Green Light", "id1", "Lorde", "Melodrama"),
                new Track("Sober", "id2", "Lorde", "Melodrama"),
                new Track("Homemade Dynamite", "id3", "Lorde", "Melodrama"),
                new Track("The Louvre", "id4", "Lorde", "Melodrama"),
                new Track("Liability", "id5", "Lorde", "Melodrama")
        ));
        when(albums.getArtistAlbumFromCurrentYear(AUTH_TOKEN, "1")).thenReturn(new Album("Melodrama", melodramaTracks));

        Playlist createdPlaylist = playlist.createPlaylist(AUTH_TOKEN);
        List<String> names = getPropertyList(createdPlaylist.getTracks(), Track::getName);

        assertThat(createdPlaylist.getName(), is("This 2018 in music"));
        assertThat(createdPlaylist.getTracks().size(), is(4));
        assertThat(names, is(asList("Green Light", "Sober", "Homemade Dynamite", "The Louvre")));
    }

    @Test
    public void shouldCreateAnEmptyPlaylistIfUserDoesntHaveAnyFollowedArtists() throws Exception {
        when(spotifyProfileService.getFollowedArtists(AUTH_TOKEN)).thenReturn(new ArrayList<>());

        Playlist createdPlaylist = playlist.createPlaylist("randomAuthToken");

        assertThat(createdPlaylist.getName(), is("This 2018 in music"));
        assertThat(createdPlaylist.getTracks().size(), is(0));
    }

    private List<String> getPropertyList(List<Track> tracks, Function<Track, String> propertyToGet) {
        return tracks.stream().map(propertyToGet).collect(toList());
    }
}