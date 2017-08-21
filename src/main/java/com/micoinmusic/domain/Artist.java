package com.micoinmusic.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Artist {
    private String name;
    private String id;
    private List<Album> albums;
}
