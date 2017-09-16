package com.micoinmusic.domain.dependencies;

import com.micoinmusic.domain.Album;

import java.util.List;

public interface AlbumsService {
    public List<Album> getAlbums(String authToken, String artistId);
    public Album getLastAlbumReleaseDate(String authToken, String albumId);
}
