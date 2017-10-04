package com.micoinmusic.controllers;

import com.micoinmusic.domain.Playlist;
import com.micoinmusic.domain.services.PlaylistService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class PlaylistController {

    private static final Logger logger = getLogger(PlaylistController.class);

    private PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/playlists")
    public Playlist create(@RequestParam("authToken") String authToken) {
        logger.info("creating playlist with the following authorization token: " + authToken);
        return playlistService.create(authToken);
    }
}
