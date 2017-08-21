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
        String authToken = "BQAzjAmVDmUxeuOMqvD5wr-89-1E4Lof-UYr-RNlQ_INSID02tpIAouUSfg95tVPO6QGDCbNMldWv8VpCUVUC_ReOqV64AhhqLCIp-Yq8zCSK9WWWdNFs7qhjJdT7QmCHwXEhlC0lcK6Br65opcnXc9PB__fkSGOnvEu9GGd3ibCB2lwazo7jPwZ4mva3ce7ypLJKVeYNKoEqpsIRF5NxLLowiw9PaL-JIHgtE5dj-SQQJ0hQr14eXHiCiObys-too-EzDVSaInjnCeEjfjY0nqjbVpWKgX_hE4fQYBjy6ejFA";

        mvc.perform(get("/artists?authToken=" + authToken).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("432")));
    }
}