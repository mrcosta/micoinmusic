package com.micoinmusic.domain;

import com.micoinmusic.domain.dependencies.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Albums {

    private ArtistsService artistsService;

    @Autowired
    public Albums(ArtistsService artistsService) {
        this.artistsService = artistsService;
    }

    public Album getArtistAlbumFromCurrentYear(String authToken, String artistId) {
        List<Album> albums = artistsService.getAlbums(authToken, artistId);
        List<Album> albumsWithReleaseDate = artistsService.getAlbumsWithReleaseDate(authToken, albums.stream().map(Album::getId).collect(toList()));

        return albumsWithReleaseDate.stream().filter(Album::isFromCurrentYear).findFirst().orElse(null);
    }
}
