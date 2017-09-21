package com.micoinmusic.domain.services;

import com.micoinmusic.domain.Playlist;
import com.micoinmusic.domain.Track;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistServiceTest {

    @Test
    public void shouldCreatePlaylistForTheGivenYear() {
        int currentYear = LocalDate.now().getYear();

        Playlist playlist = mock(Playlist.class);
        when(playlist.createPlaylist("randomAuthToken")).thenReturn(new Playlist("This 2017 in music", List.of(new Track("Music", "id", "Artist", "Album")), currentYear));
        Playlist createdPlaylist = new PlaylistService(playlist).create("randomAuthToken");

        assertThat(createdPlaylist.getName(), is("This 2017 in music"));
        assertThat(createdPlaylist.getYear(), is(currentYear));
    }
}