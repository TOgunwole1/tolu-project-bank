package com.mudson.repository;

import com.mudson.dto.EmailDetails;

public interface EmailServices {

    void sendEmailAlerts(EmailDetails emailDetails);
}
