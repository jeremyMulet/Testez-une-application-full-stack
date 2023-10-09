package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Jérémy MULET on 08/10/2023.
 */

public class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testBlankEmail() {
        LoginRequest loginRequest = new LoginRequest("", "password123");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testBlankPassword() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testBlankEmailAndPassword() {
        LoginRequest loginRequest = new LoginRequest("", "");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertEquals(2, violations.size());
    }

    @Test
    public void testNullEmail() {
        LoginRequest loginRequest = new LoginRequest(null, "password123");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullPassword() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", null);
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullEmailAndPassword() {
        LoginRequest loginRequest = new LoginRequest(null, null);
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertEquals(2, violations.size());
    }

}
