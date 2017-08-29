package com.micoinmusic.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Playlist {
    private String name;
    private List<Track> tracks;
    private int year;

    public Playlist createPlaylist(String randomAuthToken) {
        return null;
    }
}
