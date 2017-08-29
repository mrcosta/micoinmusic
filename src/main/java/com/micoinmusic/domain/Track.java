package com.micoinmusic.domain;

import lombok.Getter;

@Getter
public class Track {
    private String name;
    private String id;
    private Artist artist;
    private Album album;
    private int popularity;
}
