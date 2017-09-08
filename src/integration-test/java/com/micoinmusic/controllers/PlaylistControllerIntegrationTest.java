package com.micoinmusic.controllers;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaylistControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        WebServer webServer = new WebServer();
        webServer.configure(routes ->
            routes.get("/v1/me/following?type=artist&limit=50", addResponse("requests_stubs/profile/followed_artists.json"))
        );

        webServer.start(4040);
    }

    @Test
    @Ignore
    public void shouldSayHi() throws Exception {
        String authToken = "BQAzjAmVDmUxeu";

        mvc.perform(post("/playlists?authToken=" + authToken).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toString("responses/playlist.json")));
    }

    private Payload addResponse(String jsonPath) {
        return new Payload(APPLICATION_JSON_UTF8_VALUE, toString(jsonPath));
    }

    private String toString(String jsonPath) {
        try {
            Path responsePath = Paths.get(getClass().getClassLoader().getResource(jsonPath).toURI());
            return new String(Files.readAllBytes(responsePath));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}