package com.micoinmusic.controllers;

import com.micoinmusic.domain.Track;
import com.micoinmusic.domain.services.PlaylistService;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;

public class PlaylistControllerTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    @Ignore
    public void shouldSayHi() {
        PlaylistService playlistService = mock(PlaylistService.class);
        PlaylistController playlistController = new PlaylistController(playlistService);

        List<Track> tracks = Arrays.asList();
//        Playlist playlist = new Playlist("This 2018 in music", as  LocalDate.now().getYear());
//        when(playlistService.create("authToken")).thenReturn();

//        assertThat(, is(nullValue()));
        this.outputCapture.expect(containsString("hello"));
    }
}