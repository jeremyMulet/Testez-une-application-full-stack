package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private final Teacher teacher = Teacher.builder()
            .id(2L)
            .firstName("Teacher")
            .lastName("Teacher")
            .build();
    private Session session;

    private final User user = User.builder()
            .id(1L)
            .email("toto@mail.com")
            .lastName("TOTO")
            .firstName("Toto")
            .password("toto")
            .admin(false)
            .build();

    @BeforeEach
    public void setup() {
        LocalDateTime date = LocalDateTime.now();
        session = new Session(3L, "session", new Date(), "Test", teacher, Arrays.asList(user, user), date, date);
    }

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
    void testDelete() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("update name");
        sessionDto.setDescription("Test description");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        mockMvc.perform(delete("/api/session/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sessionDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
