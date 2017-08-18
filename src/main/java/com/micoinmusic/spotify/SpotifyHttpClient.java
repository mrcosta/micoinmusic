package com.micoinmusic.spotify;

import com.google.gson.JsonParser;
import com.micoinmusic.spotify.exceptions.SpotifyRequestException;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class SpotifyHttpClient {

    private String baseUrl;

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
            throw new RuntimeException("Network issues during request");
        }
    }

    private String getStringResponse(Response response) throws IOException {
        if (response.code() == HTTP_OK) {
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        throw new SpotifyRequestException(response.code(), getErrorMessageFromResponse(response));
    }

    private String getErrorMessageFromResponse(Response response) throws IOException {
        return new JsonParser().parse(response.body().string()).getAsJsonObject().getAsJsonObject("error").get("message").getAsString();
    }
}
