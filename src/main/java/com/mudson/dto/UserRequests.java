package com.mudson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequests {

    private String firstName;

    private String lastName;

    private String otherName;

    private Long id;

    private String email;

    private String gender;

    private String phone;

    private String alternatePhone;

    private String address;

    private String accountNumber;

    private BigDecimal balance;

}
