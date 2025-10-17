package com.mudson.repository;

import com.mudson.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailRepo implements EmailServices {

    // Make the mail sender optional so the application (and tests) can run without a real mail bean
    @Autowired(required = false)
    private JavaMailSender mailSender;

    // Fixed property key (was malformed in original file)
    @Value("${spring.mail.username:}")
    private String senderEmail;

    @Override
    public void sendEmailAlerts(EmailDetails emailDetails) {

        // If there's no mail sender configured, skip sending during tests or in environments
        if (mailSender == null) {
            System.out.println("Mail sender not configured - skipping email send (recipient=" + emailDetails.getRecipient() + ")");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(emailDetails.getRecipient());
            message.setSubject(emailDetails.getSubject());
            message.setText(emailDetails.getBody());
            mailSender.send(message);

            System.out.println("Email sent successfully");
        } catch (Exception emailnotFound) {
            // Log and continue - avoid failing the caller for email problems
            System.err.println("Failed to send email: " + emailnotFound.getMessage());
        }
    }
}
