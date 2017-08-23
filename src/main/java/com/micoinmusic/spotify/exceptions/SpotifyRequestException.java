package com.micoinmusic.spotify.exceptions;

public class SpotifyRequestException extends RuntimeException {
    public SpotifyRequestException(String message) {
        super(message);
    }
}
