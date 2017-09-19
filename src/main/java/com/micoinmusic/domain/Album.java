package com.micoinmusic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class Album {
    private String name;
    private String id;
    private LocalDate releaseDate;
    private List<Track> tracks;

    public Album(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Album(String name, String id, LocalDate releaseDate) {
        this.name = name;
        this.id = id;
        this.releaseDate = releaseDate;
    }

    public Album(String name, List<Track> tracks) {
        this.name = name;
        this.tracks = tracks;
    }

    public boolean isFromCurrentYear() {
        return this.getReleaseDate().getYear() == LocalDate.now().getYear();
    }
}
