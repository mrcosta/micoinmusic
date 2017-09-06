package com.micoinmusic.domain.services;

import com.micoinmusic.domain.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* TODO: here I created as a class, but the ideal to use in the controller, following the patterns of Hexagonal,
    would be creating an interface to be used in the PlaylistController
 */
@Service
public class PlaylistService {

    private Playlist playlist;

    @Autowired
    public PlaylistService(Playlist playlist) {
        this.playlist = playlist;
    }

    public Playlist create(String authToken) {
        return playlist.createPlaylist(authToken);
    }
}
