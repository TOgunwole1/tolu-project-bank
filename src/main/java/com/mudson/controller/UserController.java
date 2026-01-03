package com.mudson.controller;

import com.mudson.dto.Response;
import com.mudson.dto.UserRequests;
import com.mudson.dto.EmailDetails;
import com.mudson.repository.EmailServices;
import com.mudson.service.Userservice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Userservice userservice;
    private final EmailServices emailServices;

    public UserController(Userservice userservice, EmailServices emailServices) {
        this.userservice = userservice;
        this.emailServices = emailServices;
    }

    @PostMapping("/accounts")
    public Response createAccount(@RequestBody UserRequests userRequests) {
        return userservice.createAccount(userRequests);
    }

    @PostMapping("/send-email")
    public Response sendEmail(@RequestBody EmailDetails emailDetails) {
        emailServices.sendEmailAlerts(emailDetails);
        Response resp = new Response();
        resp.setResponseCode("200");
        resp.setResponseMessage("Email sent (or attempted)");
        resp.setAccountInfo(null);
        return resp;
    }
}
