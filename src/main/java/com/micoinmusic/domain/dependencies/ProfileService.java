package com.micoinmusic.domain.dependencies;

import com.micoinmusic.domain.Artist;

import java.util.List;

public interface ProfileService {
    public List<Artist> getFollowedArtists(String authToken);
}
