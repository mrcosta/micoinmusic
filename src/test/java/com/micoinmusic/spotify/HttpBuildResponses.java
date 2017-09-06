package com.micoinmusic.spotify;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class HttpBuildResponses {

    protected MockWebServer server;
    private int DEFAULT_PORT = 4040;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start(DEFAULT_PORT);
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    protected void addUnauthorizedResponse() throws URISyntaxException, IOException {
        Path responsePath = Paths.get(getClass().getClassLoader().getResource("requests_stubs/errors/invalid_token.json").toURI());
        String responseContent = new String(Files.readAllBytes(responsePath));

        server.enqueue(new MockResponse().setResponseCode(HttpStatus.UNAUTHORIZED.value()).setBody(responseContent));
    }

    protected void addResponse(String jsonFilePath) throws URISyntaxException, IOException {
        Path responsePath = Paths.get(getClass().getClassLoader().getResource(jsonFilePath).toURI());
        String responseContent = new String(Files.readAllBytes(responsePath));

        server.enqueue(new MockResponse().setBody(responseContent));
    }
}
