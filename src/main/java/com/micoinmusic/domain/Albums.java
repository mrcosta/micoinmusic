package com.micoinmusic.domain;

import com.micoinmusic.domain.dependencies.AlbumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Albums {

    private AlbumsService albumsService;

    @Autowired
    public Albums(AlbumsService albumsService) {
        this.albumsService = albumsService;
    }

    public Album getArtistAlbumFromCurrentYear(String authToken, String artistId) {
        List<Album> albums = albumsService.getAlbums(authToken, artistId);
        Album lastAlbumWithReleaseDate = albumsService.getLastAlbumReleaseDate(authToken, albums.get(0).getId());

        // TODO: retrieve the musics with popularity here

        return lastAlbumWithReleaseDate.isFromCurrentYear() ? lastAlbumWithReleaseDate : null;
    }
}
