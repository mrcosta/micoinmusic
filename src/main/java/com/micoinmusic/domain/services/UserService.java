package com.micoinmusic.domain.services;

import com.micoinmusic.domain.dependencies.MusicStreamingService;
import com.micoinmusic.spotify.SpotifyHttpClient;
import com.micoinmusic.spotify.SpotifyMusicStreamingService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private MusicStreamingService musicStreamingService;

    public UserService() {
        SpotifyHttpClient spotifyHttpClient = new SpotifyHttpClient("https://api.spotify.com/");
        musicStreamingService = new SpotifyMusicStreamingService(spotifyHttpClient);
    }

    public String getFollowedArtists(String authToken) {
        return String.valueOf(musicStreamingService.getFollowedArtists(authToken).size());
    }
}
