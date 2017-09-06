package com.micoinmusic.domain;

import com.micoinmusic.domain.dependencies.AlbumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Albums {

    private AlbumsService albumsService;

    @Autowired
    public Albums(AlbumsService albumsService) {
        this.albumsService = albumsService;
    }

    public Album getArtistAlbumFromCurrentYear(String authToken, String artistId) {
        List<Album> albums = albumsService.getAlbums(authToken, artistId);
        List<Album> albumsWithReleaseDate = albumsService.getAlbumsWithReleaseDate(authToken, albums.stream().map(Album::getId).collect(toList()));

        // TODO: retrieve the musics with popularity here

        return albumsWithReleaseDate.stream().filter(Album::isFromCurrentYear).findFirst().orElse(null);
    }
}
