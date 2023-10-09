package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jérémy MULET on 09/10/2023.
 */
public class TeacherTest {

    @Test
    public void testTeacherBuilder() {
        Long id = 1L;
        String lastName = "Toto";
        String firstName = "Test";
        LocalDateTime localDateTime = LocalDateTime.now();

        Teacher teacher = Teacher.builder()
                .id(id)
                .lastName(lastName)
                .firstName(firstName)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();

        assertEquals(id, teacher.getId());
        assertEquals(lastName, teacher.getLastName());
        assertEquals(firstName, teacher.getFirstName());
    }

    @Test
    public void testTeacherSettersAndGetters() {
        Long id = 1L;
        String lastName = "Toto";
        String firstName = "Test";
        LocalDateTime localDateTime = LocalDateTime.now();

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setLastName(lastName);
        teacher.setFirstName(firstName);
        teacher.setCreatedAt(localDateTime);
        teacher.setUpdatedAt(localDateTime);

        assertEquals(id, teacher.getId());
        assertEquals(lastName, teacher.getLastName());
        assertEquals(firstName, teacher.getFirstName());
    }
}
