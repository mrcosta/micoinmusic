package com.micoinmusic.spotify.parsers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SpotifyAlbumJsonParserTest {

    private SpotifyAlbumJsonParser albumParser;

    @Test
    public void shouldGetAlbumsName() {
        JsonObject json = new JsonParser().parse("{'name': 'Drones'}").getAsJsonObject();

        albumParser = new SpotifyAlbumJsonParser(json);

        assertThat(albumParser.getName(), is("Drones"));
    }

    @Test
    public void shouldGetAlbumsId() {
        JsonObject json = new JsonParser().parse("{'id': 'xYTaB'}").getAsJsonObject();

        albumParser = new SpotifyAlbumJsonParser(json);

        assertThat(albumParser.getId(), is("xYTaB"));
    }

    @Test
    public void shouldGetAlbumsReleaseDate() {
        JsonObject json = new JsonParser().parse("{'release_date': '2015-06-04'}").getAsJsonObject();

        albumParser = new SpotifyAlbumJsonParser(json);

        assertThat(albumParser.getReleaseDate().getYear(), is(2015));
    }

    @Test
    public void shouldReturnNullIfThereIsNoReleaseDate() {
        albumParser = new SpotifyAlbumJsonParser(new JsonObject());

        assertThat(albumParser.getReleaseDate(), is(nullValue()));
    }

    @Test
    public void shouldGetAlbumsArtist() {
        JsonObject json = new JsonParser().parse("{'artists': [{'name': 'Muse'}]}").getAsJsonObject();

        albumParser = new SpotifyAlbumJsonParser(json);

        assertThat(albumParser.getArtist(), is("Muse"));
    }
}