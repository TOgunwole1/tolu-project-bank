package com.mudson.service;

import com.mudson.entity.Login;

public interface LoginService {
    Login findByUsername(String username);
    Login save(Login login);
    boolean authenticate(String username, String rawPassword);
}
