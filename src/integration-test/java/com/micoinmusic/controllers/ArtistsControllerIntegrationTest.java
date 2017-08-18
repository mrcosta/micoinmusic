package com.micoinmusic.controllers;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArtistsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Ignore
    public void shouldSayHi() throws Exception {
        String authToken = "BQDPGgmMtH1pMBKpg7EU8IfHfvDVi9smoY8eYxOTthoLw6sVvEZzAMZhHFWaoG6Dv_TginHHJbNkO7qH1NV1OlPUwVKsyGci39KgrsaFfzJLPSFa14HCG3qRXRL579xlJhmnaBha71ItsKFD7NjI0Q";

        mvc.perform(get("/artists?authToken=" + authToken).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("432")));
    }
}