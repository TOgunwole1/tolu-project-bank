package com.mudson.controller;

import com.mudson.dto.Response;
import com.mudson.dto.UserRequests;
import com.mudson.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    Userservice userservice;

    @org.springframework.web.bind.annotation.PostMapping("/accounts")
    public Response createAccount(@RequestBody UserRequests userRequests) {
        return userservice.createAccount(userRequests);

    }

}
