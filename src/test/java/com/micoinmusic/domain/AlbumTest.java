package com.micoinmusic.domain;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class AlbumTest {

    @Test
    public void shouldReturnTrueIfAlbumIsFromCurrentYear() throws Exception {
        LocalDate releaseDate = LocalDate.now();
        Album album = new Album("Album x", "idAlbumX", releaseDate);

        assertThat(album.isFromCurrentYear(), is(true));
    }

    @Test
    public void shouldReturnFalseIfAlbumIsNotFromCurrentYear() throws Exception {
        LocalDate releaseDate = LocalDate.of(2015, 2, 2);
        Album album = new Album("Album x", "idAlbumX", releaseDate);

        assertThat(album.isFromCurrentYear(), is(false));
    }
}