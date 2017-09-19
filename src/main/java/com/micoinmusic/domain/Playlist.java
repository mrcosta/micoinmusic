package com.micoinmusic.domain;

import com.micoinmusic.domain.dependencies.ProfileService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Playlist {

    private static final int MAX_TRACKS = 4;

    @Getter private String name;
    @Getter private List<Track> tracks;
    @Getter private int year;

    private ProfileService profileService;
    private Albums albums;

    @Autowired
    public Playlist(ProfileService profileService, Albums albums) {
        this.profileService = profileService;
        this.albums = albums;
    }

    public Playlist(String name, List<Track> tracks, int year) {
        this.name = name;
        this.tracks = tracks;
        this.year = year;
    }

    public Playlist createPlaylist(String authToken) {
        List<Artist> artists = profileService.getFollowedArtists(authToken);
        List<Album> currentYearAlbums = artists.stream().map(artist -> albums.getArtistAlbumFromCurrentYear(authToken, artist.getId())).filter(album -> album != null).collect(toList());
        List<Track>  tracks = currentYearAlbums.stream().flatMap(album -> album.getTracks().stream().limit(MAX_TRACKS)).collect(toList());

        return new Playlist(getPlaylistName(), tracks, LocalDate.now().getYear());
    }

    private String getPlaylistName() {
        return "This " + LocalDate.now().getYear() + " in music";
    }
}
