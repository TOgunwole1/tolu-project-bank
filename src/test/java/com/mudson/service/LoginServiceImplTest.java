package com.mudson.service;

import com.mudson.entity.Login;
import com.mudson.repository.LoginRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {
    @Mock
    private LoginRepo loginRepo;

    @InjectMocks
    private LoginServiceImpl loginService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPasswordIsHashedOnSave() {
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        when(loginRepo.save(any(Login.class))).thenAnswer(i -> i.getArgument(0));
        Login saved = loginService.save(login);
        assertNotEquals("password", saved.getPassword());
        assertTrue(encoder.matches("password", saved.getPassword()));
    }

    @Test
    void testAuthenticateSuccess() {
        Login login = new Login();
        login.setUsername("user");
        login.setPassword(encoder.encode("password"));
        login.setEnabled(true);
        login.setLocked(false);
        when(loginRepo.findByUsername("user")).thenReturn(login);
        assertTrue(loginService.authenticate("user", "password"));
    }

    @Test
    void testAuthenticateFail() {
        when(loginRepo.findByUsername("user")).thenReturn(null);
        assertFalse(loginService.authenticate("user", "password"));
    }
}
