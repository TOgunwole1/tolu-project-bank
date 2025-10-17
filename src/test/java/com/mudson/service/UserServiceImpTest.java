package com.mudson.service;

import com.mudson.dto.UserRequests;
import com.mudson.entity.User;
import com.mudson.repository.EmailServices;
import com.mudson.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceImpTest {

    private UserRepo userRepo;
    private EmailServices emailServices;
    private UserServiceImp userService;

    @BeforeEach
    void setup() {
        userRepo = mock(UserRepo.class);
        emailServices = mock(EmailServices.class);
        // Use constructor injection (matches the implementation)
        userService = new UserServiceImp(userRepo, emailServices);
    }

    @Test
    void createAccount_happyPath_savesAndSendsEmail() {
        UserRequests req = UserRequests.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("1234567890")
                .address("123 Main St")
                .build();

        when(userRepo.existsByEmail(anyString())).thenReturn(false);
        when(userRepo.existsByPhone(anyString())).thenReturn(false);
        when(userRepo.existsByAddress(anyString())).thenReturn(false);

        User saved = new User();
        saved.setId(1L);
        saved.setFirstName("John");
        saved.setLastName("Doe");
        saved.setEmail("john@example.com");
        saved.setPhone("1234567890");
        saved.setAccountNumber("2025000001");
        saved.setBalance(BigDecimal.ZERO);

        when(userRepo.save(any(User.class))).thenReturn(saved);

        var response = userService.createAccount(req);

        assertThat(response).isNotNull();
        assertThat(response.getResponseCode()).isEqualTo("002");
        assertThat(response.getAccountInfo()).isNotNull();
        assertThat(response.getAccountInfo().getAccountNumber()).isEqualTo("2025000001");

        verify(userRepo, times(1)).save(any(User.class));
        verify(emailServices, times(1)).sendEmailAlerts(any());
    }
}
