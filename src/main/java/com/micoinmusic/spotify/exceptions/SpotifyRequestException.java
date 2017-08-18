package com.micoinmusic.spotify.exceptions;

public class SpotifyRequestException extends RuntimeException {
    private int code;
    private String message;

    public SpotifyRequestException(int code, String message) {
        super(message);
    }
}
