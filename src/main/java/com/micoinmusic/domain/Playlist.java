package com.micoinmusic.domain;

import com.micoinmusic.domain.dependencies.ProfileService;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class Playlist {

    private String name;
    private List<Track> tracks;
    private int year;

    private ProfileService profileService;
    private Albums albums;

    public Playlist(String name, List<Track> tracks, int year) {
        this.name = name;
        this.tracks = tracks;
        this.year = year;
    }

    public Playlist(ProfileService profileService, Albums albums) {
        this.profileService = profileService;
        this.albums = albums;
    }

    public Playlist createPlaylist(String authToken) {
        List<Artist> artists = profileService.getFollowedArtists(authToken);
        List<Album> currentYearAlbums = artists.stream().map(artist -> albums.getArtistAlbumFromCurrentYear(authToken, artist.getId())).filter(album -> album != null).collect(toList());
        List<Track>  tracks = currentYearAlbums.stream().flatMap(album -> album.getTracks().getItems().stream()).collect(toList());

        return new Playlist("This 2018 in music", tracks, LocalDate.now().getYear());
    }
}
