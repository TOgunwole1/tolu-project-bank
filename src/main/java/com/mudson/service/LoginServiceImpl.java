package com.mudson.service;

import com.mudson.entity.Login;
import com.mudson.repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class LoginServiceImpl implements LoginService {
    private final LoginRepo loginRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public LoginServiceImpl(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }

    @Override
    public Login findByUsername(String username) {
        return loginRepo.findByUsername(username);
    }

    @Override
    public Login save(Login login) {
        // Hash the password before saving
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        return loginRepo.save(login);
    }

    public boolean authenticate(String username, String rawPassword) {
        Login login = loginRepo.findByUsername(username);
        if (login != null && passwordEncoder.matches(rawPassword, login.getPassword()) && login.isEnabled() && !login.isLocked()) {
            return true;
        }
        return false;
    }
}
