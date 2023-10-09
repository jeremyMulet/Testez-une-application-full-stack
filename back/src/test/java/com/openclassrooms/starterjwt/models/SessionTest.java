package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jérémy MULET on 09/10/2023.
 */
public class SessionTest {

    @Test
    public void testSessionBuilder() {
        Long id = 1L;
        String name = "Session";
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        String description = "Description";
        Teacher teacher = new Teacher();
        List<User> users = new ArrayList<>();

        Session session = Session.builder()
                .id(id)
                .name(name)
                .date(date)
                .description(description)
                .teacher(teacher)
                .users(users)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();

        assertEquals(id, session.getId());
        assertEquals(name, session.getName());
        assertEquals(date, session.getDate());
        assertEquals(description, session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(localDateTime, session.getCreatedAt());
        assertEquals(localDateTime, session.getUpdatedAt());
    }

    @Test
    public void testSessionSettersAndGetters() {
        Long id = 1L;
        String name = "Session";
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        String description = "Description";
        Teacher teacher = new Teacher();
        List<User> users = new ArrayList<>();

        Session session = new Session();
        session.setId(id);
        session.setName(name);
        session.setDate(date);
        session.setDescription(description);
        session.setTeacher(teacher);
        session.setUsers(users);
        session.setCreatedAt(localDateTime);
        session.setUpdatedAt(localDateTime);

        assertEquals(id, session.getId());
        assertEquals(name, session.getName());
        assertEquals(date, session.getDate());
        assertEquals(description, session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(localDateTime, session.getCreatedAt());
        assertEquals(localDateTime, session.getUpdatedAt());
    }
}
