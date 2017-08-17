package com.micoinmusic.domain.dependencies;

import com.micoinmusic.domain.Artist;

import java.util.List;

public interface MusicStreamingService {
    public List<Artist> getFollowedArtists();
}
