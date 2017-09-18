package com.micoinmusic.spotify;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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
        server.enqueue(new MockResponse().setResponseCode(HttpStatus.UNAUTHORIZED.value()).setBody(getJsonString("requests_stubs/errors/invalid_token.json")));
    }

    protected void addResponse(String jsonFilePath) throws URISyntaxException, IOException {
        server.enqueue(getJsonMock(jsonFilePath));
    }

    protected String getJsonString(String jsonFilePath) throws URISyntaxException, IOException {
        Path responsePath = Paths.get(getClass().getClassLoader().getResource(jsonFilePath).toURI());
        return new String(Files.readAllBytes(responsePath));
    }

    protected MockResponse getJsonMock(String jsonFilePath) throws IOException, URISyntaxException {
        return new MockResponse().setBody(getJsonString(jsonFilePath));
    }

    protected void setResponses(Map<String, MockResponse> responses) {
        Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                return responses.get(request.getPath());
            }
        };

        server.setDispatcher(dispatcher);
    }
}
