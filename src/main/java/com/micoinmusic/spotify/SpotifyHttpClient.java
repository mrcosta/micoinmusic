package com.micoinmusic.spotify;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class SpotifyHttpClient {

    private String baseUrl;
    private String oauthToken;

    public String doCall(String endpoint) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .addHeader(AUTHORIZATION, "Bearer " + oauthToken)
                .url(baseUrl + endpoint)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
