package com.micoinmusic.spotify.exceptions;

public class SpotifyRequestException extends RuntimeException {
    private int code;

    public SpotifyRequestException(int code, String message) {
        super(message);
        this.code = code;
    }
}