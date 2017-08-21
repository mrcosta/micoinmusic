package com.micoinmusic.domain.services;

import com.micoinmusic.domain.dependencies.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private ProfileService profileService;

    @Autowired
    public UserService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public String getFollowedArtists(String authToken) {
        return String.valueOf(profileService.getFollowedArtists(authToken).size());
    }
}
