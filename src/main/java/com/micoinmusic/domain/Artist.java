package com.micoinmusic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Artist {
    private String name;
    private String id;
    private List<Album> albums;
}
