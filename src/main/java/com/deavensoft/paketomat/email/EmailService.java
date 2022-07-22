package com.deavensoft.paketomat.email;



import java.util.Map;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);

    int sendMailWithTemplate(EmailDetails details, Map<String, Object> modelData);
}
