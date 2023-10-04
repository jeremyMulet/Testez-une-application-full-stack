package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Jérémy MULET on 24/09/2023.
 */

@ExtendWith(MockitoExtension.class)
public class SessionServicesTests {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    private Session mockSession;
    private User mockUser;

    private User createUser() {
        return User.builder()
                .id(3L)
                .email("toto@toto.com")
                .lastName("toto")
                .firstName("toto")
                .password("toto123").build();
    }
    @BeforeEach
    public void setUp() {
        mockSession = Session.builder()
                .name("session")
                .id(1L)
                .users(new ArrayList<>(Arrays.asList(createUser()))).build();
        mockUser = User.builder()
                .id(2L)
                .email("tutu@tutu.com")
                .lastName("tutu")
                .firstName("tutu")
                .password("tutu123").build();
    }
    @Test
    public void testCreate() {
        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        Session result = sessionService.create(mockSession);

        assertThat(result).isNotNull().isEqualTo(mockSession);
        verify(sessionRepository, times(1)).save(mockSession);
    }
    @Test
    public void delete_ShouldCallDeleteById() {
        Long sessionId = 1L;
        doNothing().when(sessionRepository).deleteById(sessionId);

        sessionService.delete(sessionId);

        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void findAll_ShouldReturnAllSessions() {
        List<Session> sessions = Arrays.asList(new Session(), new Session());
        when(sessionRepository.findAll()).thenReturn(sessions);

        List<Session> result = sessionService.findAll();

        assertThat(result).isNotEmpty().isEqualTo(sessions);
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void getById_ShouldReturnSessionWhenPresent() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        Session result = sessionService.getById(1L);

        assertThat(result).isNotNull().isEqualTo(mockSession);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void getById_ShouldReturnNullWhenAbsent() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        Session result = sessionService.getById(1L);

        assertThat(result).isNull();
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void update_ShouldReturnUpdatedSession() {
        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        Session result = sessionService.update(1L, mockSession);

        assertThat(result).isNotNull().isEqualTo(mockSession);
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    public void participate_ShouldAddUserToSession() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        sessionService.participate(1L, 1L);

        assertThat(mockSession.getUsers()).contains(mockUser);
        verify(sessionRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void participate_ShouldThrowNotFoundExceptionWhenSessionOrUserNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> sessionService.participate(1L, 1L));
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void participate_ShouldThrowBadRequestExceptionWhenUserAlreadyParticipates() {
        mockSession.getUsers().add(mockUser);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mockUser));

        assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> sessionService.participate(1L, 2L));
        verify(sessionRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    public void noLongerParticipate_ShouldRemoveUserFromSession() {
        mockSession.getUsers().add(mockUser);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        sessionService.noLongerParticipate(1L, 2L);

        assertThat(mockSession.getUsers()).doesNotContain(mockUser);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testParticipate_nonExistentSession() {
        Long sessionId = 1L;
        Long userId = 2L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.participate(sessionId, userId))
                .isInstanceOf(NotFoundException.class);
        verify(sessionRepository, times(1)).findById(sessionId);
    }
}
