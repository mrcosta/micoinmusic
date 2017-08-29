package com.micoinmusic.domain;

import com.micoinmusic.spotify.SpotifyProfileService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


public class PlaylistTest {

    private final static String AUTH_TOKEN = "R4nd0M4UthToken";

    private Playlist playlist;

    @Mock private SpotifyProfileService spotifyProfileService;

    @Before
    public void setUp() {
        playlist = new Playlist(spotifyProfileService);
    }

    @Test
    @Ignore
    public void shouldCreatePlaylistForTheGivenYear() throws Exception {
        List<Artist> followedArtists = asList(new Artist("Lorde", "1"), new Artist("Arcade Fire", "5"), new Artist("Foster the People", "9"));
        when(spotifyProfileService.getFollowedArtists(AUTH_TOKEN)).thenReturn(followedArtists);

        Playlist createdPlaylist = playlist.createPlaylist(AUTH_TOKEN);

        List<String> names = getPropertyList(createdPlaylist.getTracks(), Track::getName);
        List<String> artists = getPropertyList(createdPlaylist.getTracks(), track -> track.getArtist().getName());
        List<String> albums = getPropertyList(createdPlaylist.getTracks(), track -> track.getAlbum().getName());

        assertThat(createdPlaylist.getName(), is("This 2018 in music"));
        assertThat(createdPlaylist.getTracks().size(), is(3));
        assertThat(artists, is(asList("Lorde", "Arcade Fire", "Foster The People")));
        assertThat(albums, is(asList("Melodrama", "Everything Now", "Sacred Hearts Club")));
        assertThat(names, is(asList("Green Light", "Everything Now", "Sit Next to Me")));
    }

    @Test
    @Ignore
    public void shouldNotConsiderTheArtistsThatDoesntHaveAnyAlbums() throws Exception {
        Playlist createdPlaylist = playlist.createPlaylist("randomAuthToken");
        assertThat(playlist.getName(), is("This 2018 in music"));
    }

    private List<String> getPropertyList(List<Track> tracks, Function<Track, String> propertyToGet) {
        return tracks.stream().map(propertyToGet).collect(toList());
    }
}