package com.micoinmusic.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Album {
    private String name;
    private String id;

    @SerializedName("release_date")
    private LocalDate releaseDate;

    private Tracks tracks;
}
