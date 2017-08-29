package com.micoinmusic.domain;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class PlaylistTest {

    @Test
    @Ignore
    public void shouldCreatePlaylistForTheGivenYear() throws Exception {
        Playlist playlist = new Playlist().createPlaylist("randomAuthToken");
        List<Track> tracks = playlist.getTracks();

        assertThat(playlist.getName(), is("This 2018 in music"));
        assertThat(tracks.size(), is(3));
    }

    @Test
    @Ignore
    public void shouldNotConsiderTheArtistsThatDoesntHaveAnyAlbums() throws Exception {
        Playlist playlist = new Playlist().createPlaylist("randomAuthToken");
        assertThat(playlist.getName(), is("This 2018 in music"));
    }

}