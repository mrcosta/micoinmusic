package com.micoinmusic.spotify;

import com.micoinmusic.domain.Artist;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpotifyMusicStreamingServiceTest {

    private MockWebServer server;
    private SpotifyMusicStreamingService spotifyMusicStreamingService;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();

        SpotifyHttpClient spotifyHttpClient = new SpotifyHttpClient(server.url("").toString(), "AQDxVIjCisbrCzM");
        spotifyMusicStreamingService = new SpotifyMusicStreamingService(spotifyHttpClient);
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void shouldGetAllTheFollowedArtistsByUser() throws Exception {
        addResponse("requests_stubs/followed_artists_single_request.json");

        List<Artist> followedArtists = spotifyMusicStreamingService.getFollowedArtists();
        List<String> artistsNames = followedArtists.stream().map(Artist::getName).collect(Collectors.toList());

        assertThat(followedArtists.size(), is(2));
        assertThat(artistsNames, is(Arrays.asList("Porcelain Raft", "Emerson, Lake & Palmer")));
    }

    private void addResponse(String jsonFilePath) throws URISyntaxException, IOException {
        Path responsePath = Paths.get(getClass().getClassLoader().getResource(jsonFilePath).toURI());
        String responseContent = new String(Files.readAllBytes(responsePath));

        server.enqueue(new MockResponse().setBody(responseContent));
    }
}