package com.mudson.util;

import java.time.Year;

public class AccountUtil {

    public static final String EXISTS_CODE = "001";

    public static final String EXISTS_MESSAGE = "Account already exists";

    public static final String ACC_CREATED_CODE = "002";
    public static final String ACC_CREATED_MSG = "created";

    public static String generateAccNum(){
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        //generate random number between max and min

        int randomnum = (int) Math.floor(Math.random() * (max - min + 1) + min);

        String year = String.valueOf(currentYear);
        String random = String.valueOf(randomnum);

        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(random).toString();


    }
}
