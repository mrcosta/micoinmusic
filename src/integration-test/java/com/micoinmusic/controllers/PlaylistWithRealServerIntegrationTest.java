package com.micoinmusic.controllers;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaylistWithRealServerIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Ignore
    public void shouldResponseWithTheCauseWhenNotSendingTheAuthToken() throws Exception {
        given().contentType("application/json").
        when().post("playlists").
        then().statusCode(SC_BAD_REQUEST).body(sameJSONAs(
                "{ 'error': {" +
                        "'status_id': 400," +
                        "'status': 'BAD_REQUEST'," +
                        "'message': 'Required String parameter 'authToken' is not present'" +
                    "}" +
                "}"
        ));
    }
}
