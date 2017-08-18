package com.micoinmusic.controllers;

import com.micoinmusic.domain.services.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ArtistsController {

    private static final Logger logger = getLogger(ArtistsController.class);

    private UserService userService;

    @Autowired
    public ArtistsController(UserService userService) {
        this.userService = userService;
    }

    @GET
	@RequestMapping("/artists")
    public String getArtistsNumber(@NotNull @QueryParam("authToken") String authToken) {
        logger.info("hello booter");

        return userService.getFollowedArtists(authToken);
    }
}
