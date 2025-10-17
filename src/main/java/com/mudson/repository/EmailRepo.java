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
            String recipient = (emailDetails != null) ? emailDetails.getRecipient() : null;
            System.out.println("Mail sender not configured - skipping email send (recipient=" + recipient + ")");
            return;
        }

        // Defensive null checks for emailDetails and required fields
        if (emailDetails == null) {
            System.err.println("EmailDetails is null - nothing to send");
            return;
        }

        String recipient = emailDetails.getRecipient();
        if (recipient == null || recipient.trim().isEmpty()) {
            System.err.println("Recipient is null or empty - skipping email send");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // Only set from if configured
            if (senderEmail != null && !senderEmail.trim().isEmpty()) {
                message.setFrom(senderEmail);
            }
            message.setTo(recipient);
            if (emailDetails.getSubject() != null) message.setSubject(emailDetails.getSubject());
            if (emailDetails.getBody() != null) message.setText(emailDetails.getBody());
            mailSender.send(message);

            System.out.println("Email sent successfully");
        } catch (Exception emailnotFound) {
            // Log and continue - avoid failing the caller for email problems
            System.err.println("Failed to send email: " + emailnotFound.getMessage());
        }
    }
}
