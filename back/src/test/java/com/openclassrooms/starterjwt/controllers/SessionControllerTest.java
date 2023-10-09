package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Jérémy MULET on 28/09/2023.
 */

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindById() throws Exception {

        mockMvc.perform(get("/api/session/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldFindByIdReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/session/{id}", -1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindByIdReturnBadRequest() throws Exception {

        mockMvc.perform(get("/api/session/test"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllSession() throws Exception {

        mockMvc.perform(get("/api/session").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(4L);
        sessionDto.setName("Test session");
        sessionDto.setDescription("Test description");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        mockMvc.perform(post("/api/session")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sessionDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("update name");
        sessionDto.setDescription("Test description");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        mockMvc.perform(put("/api/session/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sessionDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate_shouldReturnBadRequest() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("update name");
        sessionDto.setDescription("Test description");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        mockMvc.perform(put("/api/session/toto", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sessionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSave() throws Exception {

        mockMvc.perform(delete("/api/session/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSave_shouldReturnBadRequest() throws Exception {

        mockMvc.perform(delete("/api/session/toto", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSave_shouldReturnNotFound() throws Exception {

        mockMvc.perform(delete("/api/session/{id}", -1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testParticipate() throws Exception {
        mockMvc.perform(post("/api/session/2/participate/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testParticipate_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/a")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testParticipate_shouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/40")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testNolongerParticipate() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testNolongerParticipate_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/session/2/participate/a")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}
