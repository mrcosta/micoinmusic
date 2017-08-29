package com.micoinmusic.domain.services;

import com.micoinmusic.domain.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Playlist playlist;

    @Autowired
    public UserService(Playlist playlist) {
        this.playlist = playlist;
    }

    public String getFollowedArtists(String authToken) {
        return null;
    }
}
