package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Jérémy MULET on 28/09/2023.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("toto@toto.com", "toto!123");
        UserDetailsImpl mockUserDetails = UserDetailsImpl
                .builder()
                .username("test@email.com")
                .firstName("toto")
                .lastName("toto")
                .id(1L)
                .password("toto123")
                .build();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(mockUserDetails, null);

        when(authenticationManager.authenticate(any())).thenReturn(auth);


        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk());

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    public void testRegisterUser() throws Exception {

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("toto@toto.com");
        signUpRequest.setFirstName("toto");
        signUpRequest.setLastName("tutu");
        signUpRequest.setPassword("toto123");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode("toto123")).thenReturn("hashed");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testRegisterUserEmailAlreadyExist() throws Exception {

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("yoga@studio.com");
        signUpRequest.setFirstName("toto");
        signUpRequest.setLastName("tutu");
        signUpRequest.setPassword("toto123");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        when(passwordEncoder.encode("toto123")).thenReturn("hashed");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));


    }
}