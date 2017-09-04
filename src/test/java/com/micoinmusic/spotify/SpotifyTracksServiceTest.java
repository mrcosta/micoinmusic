package com.micoinmusic.spotify;

import com.micoinmusic.domain.Track;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpotifyTracksServiceTest extends HttpBuildResponses {

    private SpotifyTracksService spotifyTracksService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        spotifyTracksService = new SpotifyTracksService(server.url("").toString());
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    @Ignore
    public void shouldGetThePopularityForTheGivenTracks() throws Exception {
        addResponse("requests_stubs/tracks/tracks_with_popularity.json");

        List<Track> tracks = spotifyTracksService.getTracksWithPopularityInformation("AuthTokenAQDxVIjCisbrCzM", Arrays.asList("2daZovie6pc2ZK7StayD1K", "2E5tWJSusHxpaksg1yfsIu", "383QXk8nb2YrARMUwDdjQS"));
//        List<Integer> tracksPopularity = tracks.stream().map(Track::getPopularity).collect(toList());

        assertThat(tracks.size(), is(3));
//        assertThat(tracksPopularity.get(0), is(64));
//        assertThat(tracksPopularity.get(1), is(8));
//        assertThat(tracksPopularity.get(2), is(71));
        assertThat(server.takeRequest().getPath(), is("/v1/tracks?ids=2daZovie6pc2ZK7StayD1K,2E5tWJSusHxpaksg1yfsIu,383QXk8nb2YrARMUwDdjQS&market=US"));
    }
}