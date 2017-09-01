package com.micoinmusic.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Album {
    private String name;
    private String id;

    @SerializedName("release_date")
    private LocalDate releaseDate;

    private Tracks tracks;

    public Album(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Album(String name, String id, LocalDate releaseDate) {
        this.name = name;
        this.id = id;
        this.releaseDate = releaseDate;
    }

    public boolean isFromCurrentYear() {
        return this.getReleaseDate().getYear() == LocalDate.now().getYear();
    }
}
