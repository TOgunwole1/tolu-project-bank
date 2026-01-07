package com.mudson.controller;

import com.mudson.entity.Login;
import com.mudson.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean authenticated = loginService.authenticate(username, password);
        if (authenticated) {
            return "Login successful";
        } else {
            return "Invalid username or password";
        }
    }
}
