package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Jérémy MULET on 28/09/2023.
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAuthenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("toto@toto.com", "toto!123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setFirstName("test");
        signUpRequest.setLastName("test");
        signUpRequest.setPassword("test123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    public void testRegisterUserEmailAlreadyExist() throws Exception {

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("toto@toto.com");
        signUpRequest.setFirstName("toto");
        signUpRequest.setLastName("tutu");
        signUpRequest.setPassword("toto123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));

    }
}