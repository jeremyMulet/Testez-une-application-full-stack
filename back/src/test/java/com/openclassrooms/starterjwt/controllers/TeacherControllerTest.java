package com.openclassrooms.starterjwt.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Jérémy MULET on 08/10/2023.
 */

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindById() throws Exception {

        mockMvc.perform(get("/api/teacher/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testFindById_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(get("/api/teacher/toto")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindById_shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/teacher/-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAll() throws Exception {

        mockMvc.perform(get("/api/teacher").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
