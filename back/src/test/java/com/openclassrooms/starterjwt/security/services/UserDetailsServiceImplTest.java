package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by Jérémy MULET on 02/10/2023.
 */
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private final User user = User.builder()
            .id(1L)
            .email("toto@mail.com")
            .lastName("TOTO")
            .firstName("Toto")
            .password("toto")
            .admin(false)
            .build();

    private final UserDetailsImpl userDetailsMock = UserDetailsImpl.builder()
            .id(1L)
            .username("toto@mail.com")
            .firstName("Toto")
            .lastName("TOTO")
            .password("toto")
            .admin(false)
            .build();
    private final UserDetailsImpl userDetailsMock2 = UserDetailsImpl.builder()
            .id(2L)
            .username("Tutu@mail.com")
            .firstName("Toto")
            .lastName("TOTO")
            .password("tutu")
            .admin(false)
            .build();

    @Test
    public void testLoadUserByUsername() {
        String username = "toto@mail.com";

        when(userRepository.findByEmail(username)).thenReturn(Optional.ofNullable(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assert user != null;
        assertThat(userDetails.getUsername()).isEqualTo(user.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDetails.isAccountNonExpired()).isEqualTo(true);
        assertThat(userDetails.isAccountNonLocked()).isEqualTo(true);
        assertThat(userDetails.isCredentialsNonExpired()).isEqualTo(true);
        assertThat(userDetails.isEnabled()).isEqualTo(true);
        assertThat(userDetails.equals(userDetailsMock)).isEqualTo(true);
        assertThat(userDetails.equals(userDetailsMock2)).isEqualTo(false);

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void whenInvalidUsername_thenThrowUsernameNotFoundException() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userDetailsService.loadUserByUsername(user.getEmail()))
                .withMessage("User Not Found with email: " + user.getEmail());

        verify(userRepository, times(1)).findByEmail(anyString());
    }
}
