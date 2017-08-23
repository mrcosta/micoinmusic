package com.micoinmusic.spotify;

import com.google.gson.JsonParser;
import com.micoinmusic.spotify.exceptions.SpotifyRequestException;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class SpotifyHttpClient {

    public static final String API_BASE_URL = "https://api.spotify.com/";

    private String baseUrl;

    public SpotifyHttpClient() {
        this.baseUrl = API_BASE_URL;
    }

    public String doCall(String endpoint, String oauthToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .addHeader(AUTHORIZATION, "Bearer " + oauthToken)
                .url(baseUrl + endpoint)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return getStringResponse(response);
        } catch (IOException e) {
            throw new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT, "Network issues during request or problems parsing the response");
        }
    }

    private String getStringResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            return response.body().string();
        }

        throw new SpotifyRequestException(getErrorMessageFromResponse(response));
    }

    private String getErrorMessageFromResponse(Response response) throws IOException {
        return new JsonParser().parse(response.body().string()).getAsJsonObject().getAsJsonObject("error").get("message").getAsString();
    }
}
