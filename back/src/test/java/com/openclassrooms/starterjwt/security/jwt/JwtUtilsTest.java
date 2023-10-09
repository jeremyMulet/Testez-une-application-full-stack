package com.openclassrooms.starterjwt.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    private UserDetailsImpl userPrincipal;

    @BeforeEach
    public void setUp() {
        userPrincipal = UserDetailsImpl.builder()
                .id(1L)
                .username("email@test.com")
                .password("password")
                .firstName("fname")
                .lastName("lname")
                .build();
        // Utiliser ReflectionTestUtils pour injecter des dépendances
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "testSecret");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 3600000); // par exemple, 1 heure
    }

    @Test
    public void testGenerateJwtToken() {

        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        String jwt = jwtUtils.generateJwtToken(authentication);

        assertThat(jwt).isNotNull();
        assertThat(jwt).isNotEmpty();
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        String mockJwt = jwtUtils.generateJwtToken(authentication);
        String username = jwtUtils.getUserNameFromJwtToken(mockJwt);

        assertThat(username).isEqualTo("email@test.com");
    }

    @Test
    public void testValidateJwtToken() {
        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        String mockJwt = jwtUtils.generateJwtToken(authentication);
        boolean isValid = jwtUtils.validateJwtToken(mockJwt);

        assertThat(isValid).isTrue();
    }

    @Test
    public void testInvalidateJwtTokenWithModifiedSignature() {
        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        String mockJwt = jwtUtils.generateJwtToken(authentication) + "tampered";
        boolean isValid = jwtUtils.validateJwtToken(mockJwt);

        assertThat(isValid).isFalse();
    }
}
