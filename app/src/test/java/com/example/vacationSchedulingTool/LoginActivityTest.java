package com.example.vacationSchedulingTool;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.vacationSchedulingTool.UI.LoginActivity;

public class LoginActivityTest {

    @Test
    public void test_ValidEmail_ReturnsTrue() {
        LoginActivity loginActivity = new LoginActivity();
        assertTrue(loginActivity.isValidEmail("test@example.com"));
    }

    @Test
    public void test_InvalidEmail_ReturnsFalse() {
        LoginActivity loginActivity = new LoginActivity();
        assertFalse(loginActivity.isValidEmail("invalid_email"));
    }

    @Test
    public void test_ValidPassword_ReturnsTrue() {
        LoginActivity loginActivity = new LoginActivity();
        assertTrue(loginActivity.validatePassword("StrongPassword@123"));
    }

    @Test
    public void test_InvalidPassword_ReturnsFalse() {
        LoginActivity loginActivity = new LoginActivity();
        assertFalse(loginActivity.validatePassword("weak"));
    }
}
