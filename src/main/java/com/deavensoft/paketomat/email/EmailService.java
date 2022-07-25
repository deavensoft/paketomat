package com.deavensoft.paketomat.email;



import java.util.Map;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

    int sendMailWithTemplate(EmailDetails details, Map<String, Object> modelData);
}
