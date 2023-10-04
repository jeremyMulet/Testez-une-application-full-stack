package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Jérémy MULET on 28/09/2023.
 */

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {


    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        teacherService = new TeacherService(teacherRepository);
    }

    @Test
    public void testFindAll() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        List<Teacher> teachers = teacherService.findAll();

        assertThat(teachers).hasSize(2).contains(teacher1, teacher2);
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_found() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher foundTeacher = teacherService.findById(1L);

        assertThat(foundTeacher).isNotNull().isEqualToComparingFieldByField(teacher);
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_notFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Teacher foundTeacher = teacherService.findById(1L);

        assertThat(foundTeacher).isNull();
        verify(teacherRepository, times(1)).findById(1L);
    }
}

