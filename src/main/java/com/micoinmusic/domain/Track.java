package com.micoinmusic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Track {
    private String name;
    private String id;
    private String artist;
    private String album;

    public Track(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
