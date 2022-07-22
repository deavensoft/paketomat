package com.deavensoft.paketomat.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }
    @PostMapping("/sendMailWithTemplate")
    public int sendMailWithTemplate(
            @RequestBody EmailDetails details)
    {
        Map<String, Object> model = new HashMap<>();
        model.put("msgBody",details.getMsgBody());
        int status
                = emailService.sendMailWithTemplate(details, model);

        return status;
    }
}
