package com.micoinmusic.controllers;

import com.micoinmusic.spotify.HttpBuildResponses;
import org.junit.Before;
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
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaylistControllerIntegrationTest extends HttpBuildResponses {

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCreatePlaylist() throws Exception {
        Map responses = Map.of(
                "/v1/me/following?type=artist&limit=50", getJsonMock("requests_stubs/profile/followed_artists.json"),
                "/v1/artists/00FQb4jTyendYWaN8pK0wa/albums?album_type=album&market=US&limit=50", getJsonMock("requests_stubs/albums/lana_albums.json"),
                "/v1/artists/01F64hXfIisZbwBf1VCwQT/albums?album_type=album&market=US&limit=50", getJsonMock("requests_stubs/albums/carne_albums.json"),
                "/v1/artists/02NfyD6AlLA12crYzw5YcR/albums?album_type=album&market=US&limit=50", getJsonMock("requests_stubs/albums/janes_albums.json"),
                "/v1/albums?ids=7xYiTrbTL57QO0bb4hXIKo&market=US", getJsonMock("requests_stubs/albums/lana_albums_with_rd.json"),
                "/v1/albums?ids=3krb3LsOogMryKOllCsYAL&market=US", getJsonMock("requests_stubs/albums/carne_albums_with_rd.json"),
                "/v1/albums?ids=6XQRLJZqxADFmJYbDUOpGN&market=US", getJsonMock("requests_stubs/albums/janes_albums_with_rd.json"));
        setResponses(responses);

        mvc.perform(post("/playlists?authToken=BQAzjAmVDmUxeu").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toString("responses/playlist.json")));
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