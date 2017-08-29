package com.micoinmusic.domain;

import com.micoinmusic.domain.dependencies.ProfileService;
import lombok.Getter;

import java.util.List;

@Getter
public class Playlist {

    private ProfileService profileService;

    private String name;
    private List<Track> tracks;
    private int year;

    public Playlist(ProfileService profileService) {
        this.profileService = profileService;
    }

    public Playlist createPlaylist(String randomAuthToken) {
        return null;
    }
}
