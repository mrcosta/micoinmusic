package com.micoinmusic.spotify.parsers;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.micoinmusic.domain.Track;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpotifyTracksJsonParserTest {

    private SpotifyTracksJsonParser tracksParser;

    @Test
    public void shouldGetTracksFromAlbum() {
        JsonObject json = new JsonParser().parse("{" +
                "'name': 'Drones'," +
                "'artists': [{'name': 'Muse'}]," +
                "'tracks': {'items': [{'name': 'Dead Inside', 'id': '2daZovie6pc2ZK7StayD1K'}]}" +
        "}").getAsJsonObject();

        tracksParser = new SpotifyTracksJsonParser(json);
        List<Track> tracks = tracksParser.getTracks();

        assertThat(tracks.get(0).getName(), is("Dead Inside"));
        assertThat(tracks.get(0).getId(), is("2daZovie6pc2ZK7StayD1K"));
        assertThat(tracks.get(0).getAlbum(), is("Drones"));
        assertThat(tracks.get(0).getArtist(), is("Muse"));
    }

    @Test
    public void shouldReturnEmptyListIfThereIsNoTracks() {
        JsonObject json = new JsonParser().parse("{" +
                "'name': 'Drones'," +
                "'artists': [{'name': 'Muse'}]" +
        "}").getAsJsonObject();

        tracksParser = new SpotifyTracksJsonParser(json);
        List<Track> tracks = tracksParser.getTracks();

        assertThat(tracks.isEmpty(), is(true));
    }
}