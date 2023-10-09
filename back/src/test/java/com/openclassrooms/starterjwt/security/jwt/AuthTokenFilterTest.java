package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthTokenFilterTest {

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void testDoFilterInternal() throws Exception {
        String mockToken = "mockToken";
        String mockUsername = "mockUsername";
        UserDetails mockUserDetails = mock(UserDetails.class);

        // Configurer la requête
        request.addHeader("Authorization", "Bearer " + mockToken);

        // Configurer les mocks pour simuler un token valide et un utilisateur existant
        when(jwtUtils.validateJwtToken(mockToken)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(mockToken)).thenReturn(mockUsername);
        when(userDetailsService.loadUserByUsername(mockUsername)).thenReturn(mockUserDetails);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Vérifier que le contexte de sécurité est configuré
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // Vérifier que le filterChain est exécuté
        verify(filterChain).doFilter(request, response);
    }

}
