package com.openclassrooms.starterjwt.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Jérémy MULET on 08/10/2023.
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindById() throws Exception {

        mockMvc.perform(get("/api/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testFindById_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(get("/api/user/toto")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindById_shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/user/-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "yoyo@yoyo.com")
    void testSave() throws Exception {

        mockMvc.perform(delete("/api/user/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "yoyo@yoyo.com")
    void testSave_shouldReturnNotFound() throws Exception {

        mockMvc.perform(delete("/api/user/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "yoyo@yoyo.com")
    void testSave_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(delete("/api/user/toto"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
