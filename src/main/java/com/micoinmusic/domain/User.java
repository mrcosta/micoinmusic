package com.micoinmusic.domain;

import com.micoinmusic.domain.dependencies.MusicStreamingService;

import java.util.List;

public class User {
    private MusicStreamingService service;
    private List<String> followingArtists;
}
