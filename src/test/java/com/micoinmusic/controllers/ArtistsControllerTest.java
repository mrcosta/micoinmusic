package com.micoinmusic.controllers;

import com.micoinmusic.domain.services.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArtistsControllerTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void shouldSayHi() {
        UserService userService = mock(UserService.class);
        ArtistsController artistsController = new ArtistsController(userService);

        when(userService.getFollowedArtists("authToken")).thenReturn("430");

        assertThat(artistsController.getArtistsNumber("authToken"), is("430"));
        this.outputCapture.expect(containsString("hello"));
    }
}