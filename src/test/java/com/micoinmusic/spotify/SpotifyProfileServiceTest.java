package com.micoinmusic.spotify;

import com.micoinmusic.domain.Artist;
import com.micoinmusic.spotify.exceptions.SpotifyRequestException;
import com.micoinmusic.spotify.parsers.SpotifyJsonParser;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpotifyProfileServiceTest extends HttpBuildResponses {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    private SpotifyProfileService profileService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        profileService = new SpotifyProfileService(new SpotifyJsonParser(), new SpotifyHttpClient(server.url("").toString()));
    }

    @Test
    public void shouldGetAllTheFollowedArtistsByUser() throws Exception {
        addResponse("requests_stubs/profile/followed_artists_single_request.json");

        List<Artist> followedArtists = profileService.getFollowedArtists("AQDxVIjCisbrCzM");
        List<String> artistsNames = followedArtists.stream().map(Artist::getName).collect(Collectors.toList());

        assertThat(followedArtists.size(), is(2));
        assertThat(artistsNames, is(Arrays.asList("Porcelain Raft", "Emerson, Lake & Palmer")));
        assertThat(server.takeRequest().getPath(), is("/v1/me/following?type=artist&limit=50"));
    }

    @Test
    public void shouldGetAllTheFollowedArtistsByUserWhenIsNecessaryMoreThanOneRequest() throws Exception {
        addResponse("requests_stubs/profile/followed_artists.json");
        addResponse("requests_stubs/profile/followed_artists_next.json");

        List<Artist> followedArtists = profileService.getFollowedArtists("AQDxVIjCisbrCzM");
        List<String> artistsNames = followedArtists.stream().map(Artist::getName).collect(Collectors.toList());

        assertThat(followedArtists.size(), is(4));
        assertThat(artistsNames, is(Arrays.asList("Porcelain Raft", "Emerson, Lake & Palmer", "Avenged Sevenfold", "Boogarins")));
        assertThat(server.takeRequest().getPath(), is("/v1/me/following?type=artist&limit=50"));
        assertThat(server.takeRequest().getPath(), is("/v1/me/following?type=artist&limit=50&after=0nCiidE5GgDrc5kWN3NZgZ"));
    }

    @Test
    public void shouldReturnAnEmptyListOfArtists() throws Exception {
        addResponse("requests_stubs/profile/followed_artists_empty.json");
        assertThat(profileService.getFollowedArtists("AQDxVIjCisbrCzM").size(), is(0));
    }

    @Test
    public void shouldThrowExceptionWhenAccessTokenIsInvalid() throws Exception {
        addUnauthorizedResponse();

        expectedException.expect(SpotifyRequestException.class);
        expectedException.expectMessage("Invalid access token");

        profileService.getFollowedArtists("AQDxVIjCisbrCzM");
    }

    @Test
    public void shouldThrowExceptionWhenSomethingWentWrongDuringARequest() throws Exception {
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        expectedException.expect(HttpServerErrorException.class);
        expectedException.expectMessage("Network issues during request");

        profileService.getFollowedArtists("AQDxVIjCisbrCzM");
    }
}