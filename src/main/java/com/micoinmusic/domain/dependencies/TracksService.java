package com.micoinmusic.domain.dependencies;

import com.micoinmusic.domain.Track;

import java.util.List;

public interface TracksService {
    public List<Track> getTracksWithPopularityInformation(String authToken, List<String> tracksId);
}
