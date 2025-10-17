package com.mudson.service;

import com.mudson.dto.Response;
import com.mudson.dto.UserRequests;

public interface Userservice {
    Response createAccount(UserRequests userrequest);


}
