package com.micoinmusic.controllers;

import com.micoinmusic.domain.Playlist;
import com.micoinmusic.domain.Track;
import com.micoinmusic.domain.services.PlaylistService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistControllerTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    private PlaylistController playlistController;
    private PlaylistService playlistService;

    @Before
    public void setUp() {
        playlistService = mock(PlaylistService.class);
        playlistController = new PlaylistController(playlistService);
    }

    @Test
    public void shouldCreatePlaylistForTheGivenYear() {
        int currentYear = LocalDate.now().getYear();

        Playlist stubedPlaylist = new Playlist("This 2018 in music", List.of(new Track("Music", "id", "Artist", "Album")), currentYear);
        when(playlistService.create("randomAuthToken")).thenReturn(stubedPlaylist);
        Playlist playlist = playlistController.create("randomAuthToken");

        assertThat(playlist.getName(), is("This 2018 in music"));
        assertThat(playlist.getYear(), is(currentYear));
        this.outputCapture.expect(containsString("creating playlist with the following authorization token: randomAuthToken"));
    }

    @Test
    @Ignore
    public void shouldThrowAnErrorWithDetailedInformationWhenAuthTokenIsNotSent() {
        Playlist playlist = playlistController.create(null);

        this.outputCapture.expect(containsString("creating playlist with the following authorization token: randomAuthToken"));
    }
}