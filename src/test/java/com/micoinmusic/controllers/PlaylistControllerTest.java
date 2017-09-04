package com.micoinmusic.controllers;

import com.micoinmusic.domain.services.PlaylistService;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistControllerTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void shouldSayHi() {
        PlaylistService playlistService = mock(PlaylistService.class);
        PlaylistController playlistController = new PlaylistController(playlistService);

        // TODO: improve test
        when(playlistService.create("authToken")).thenReturn(null);

        assertThat(playlistController.create("authToken"), is(nullValue()));
        this.outputCapture.expect(containsString("hello"));
    }
}