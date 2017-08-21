package com.micoinmusic.domain.dependencies;

import com.micoinmusic.domain.Album;

import java.util.List;

public interface ArtistsService {
    public List<Album> getAlbums(String authToken, String albumId);
}
