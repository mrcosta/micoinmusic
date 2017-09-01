package com.micoinmusic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Tracks {
    private List<Track> items;
}
