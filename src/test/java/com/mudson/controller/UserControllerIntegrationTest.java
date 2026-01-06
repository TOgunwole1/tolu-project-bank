package com.mudson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mudson.dto.UserRequests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAccount_withValidData_returnsSuccess() throws Exception {
        UserRequests req = UserRequests.builder()
                .firstName("Tolu")
                .lastName("Tester")
                .email("ttolu2458@gmail.com")
                .phone("1234567890")
                .address("123 Main St")
                .build();

        mockMvc.perform(post("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InR0b2x1MjQ1OEBnbWFpbC5jb20iLCJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlRvbHUiLCJpYXQiOjE1MTYyMzkwMjJ9.2wQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw")
            .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.responseCode").exists())
            .andExpect(jsonPath("$.accountInfo.accountNumber").exists());
    }
}
