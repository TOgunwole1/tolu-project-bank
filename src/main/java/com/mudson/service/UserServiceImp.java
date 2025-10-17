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

            return Response.builder()
                    .responseCode(AccountUtil.EXISTS_CODE)
                    .responseMessage(AccountUtil.EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        /*
         * Create a new user account and save it to the database.
         */
        User newUser = User.builder()
                .firstName(userrequest.getFirstName())
                .lastName(userrequest.getLastName())
                .otherName(userrequest.getOtherName())
                .gender(userrequest.getGender())
                .id(userrequest.getId())
                .phone(userrequest.getPhone())
                .email(userrequest.getEmail())
                .alternatePhone(userrequest.getAlternatePhone())
                .address(userrequest.getAddress())
                .balance(BigDecimal.ZERO)  // Default balance
                .accountNumber(AccountUtil.generateAccNum())  // Generate account number
                .build();

        User savedUser = userRepo.save(newUser);  // Save the new user to the database

        //Send email Alerts
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .body("Account created successfully,\n" +
                        "Account Details:\n" +
                        "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\n" +
                        "Account Email: " + savedUser.getEmail() + "\n" +
                        "Account Phone: " + savedUser.getPhone() + "\n" +
                        "Account Number: " + savedUser.getAccountNumber() + "\n" +
                        "Creation Time: " + savedUser.getCreatedAt())
                .build();
        emailServices.sendEmailAlerts(emailDetails);

        return Response.builder()
                .responseCode(AccountUtil.ACC_CREATED_CODE)
                .responseMessage(AccountUtil.ACC_CREATED_MSG)
                .accountInfo(AccountInfo.builder()
                        .balance(savedUser.getBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .build())
                .build();
    }
}
