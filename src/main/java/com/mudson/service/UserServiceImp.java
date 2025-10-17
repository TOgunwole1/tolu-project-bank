package com.mudson.service;

import com.mudson.dto.AccountInfo;
import com.mudson.dto.EmailDetails;
import com.mudson.dto.Response;
import com.mudson.dto.UserRequests;
import com.mudson.entity.User;
import com.mudson.repository.EmailServices;
import com.mudson.repository.UserRepo;
import com.mudson.util.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service  // Placed above the class definition
public class UserServiceImp implements Userservice {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    EmailServices emailServices;

    @Override
    public Response createAccount(UserRequests userrequest) {
        /*
         * Check if a user with the same email, address, or phone already exists.
         */
        if (userRepo.existsByEmail(userrequest.getEmail()) ||
                userRepo.existsByAddress(userrequest.getAddress()) ||
                userRepo.existsByPhone(userrequest.getPhone())) {

            Response resp = new Response();
            resp.setResponseCode(AccountUtil.EXISTS_CODE);
            resp.setResponseMessage(AccountUtil.EXISTS_MESSAGE);
            resp.setAccountInfo(null);
            return resp;
        }

        /*
         * Create a new user account and save it to the database.
         */
        User newUser = new User();
        newUser.setFirstName(userrequest.getFirstName());
        newUser.setLastName(userrequest.getLastName());
        newUser.setOtherName(userrequest.getOtherName());
        newUser.setGender(userrequest.getGender());
        newUser.setId(userrequest.getId());
        newUser.setPhone(userrequest.getPhone());
        newUser.setEmail(userrequest.getEmail());
        newUser.setAlternatePhone(userrequest.getAlternatePhone());
        newUser.setAddress(userrequest.getAddress());
        newUser.setBalance(BigDecimal.ZERO); // Default balance
        newUser.setAccountNumber(AccountUtil.generateAccNum());

        User savedUser = userRepo.save(newUser);  // Save the new user to the database

        //Send email Alerts
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(savedUser.getEmail());
        emailDetails.setSubject("ACCOUNT CREATION");
        StringBuilder body = new StringBuilder();
        body.append("Account created successfully,\n");
        body.append("Account Details:\n");
        body.append("Account Name: ").append(savedUser.getFirstName()).append(" ").append(savedUser.getLastName()).append("\n");
        body.append("Account Email: ").append(savedUser.getEmail()).append("\n");
        body.append("Account Phone: ").append(savedUser.getPhone()).append("\n");
        body.append("Account Number: ").append(savedUser.getAccountNumber()).append("\n");
        body.append("Creation Time: ").append(savedUser.getCreatedAt());
        emailDetails.setBody(body.toString());
        emailServices.sendEmailAlerts(emailDetails);

        AccountInfo accInfo = new AccountInfo();
        accInfo.setBalance(savedUser.getBalance());
        accInfo.setAccountNumber(savedUser.getAccountNumber());
        accInfo.setAccountName(savedUser.getFirstName() + " " + savedUser.getLastName());

        Response resp = new Response();
        resp.setResponseCode(AccountUtil.ACC_CREATED_CODE);
        resp.setResponseMessage(AccountUtil.ACC_CREATED_MSG);
        resp.setAccountInfo(accInfo);
        return resp;
    }
}
