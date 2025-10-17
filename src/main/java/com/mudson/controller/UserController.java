package com.mudson.controller;

import com.mudson.dto.Response;
import com.mudson.dto.UserRequests;
import com.mudson.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    Userservice userservice;

    public Response createAccount(@RequestBody UserRequests userRequests) {
        return userservice.createAccount(userRequests);

    }

}
