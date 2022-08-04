package com.deavensoft.paketomat.email;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @PostMapping("/sendMail")
    @Operation(summary = "sending basic mail")
    @ApiResponse(responseCode = "200", description = "Mail has been sent.")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

    @PostMapping("/sendMailWithAttachment")
    @Operation(summary = "Sending basic mail with attachment.")
    @ApiResponse(responseCode = "200", description = "Mail with attachment has been sent.")
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }
    @PostMapping("/sendMailWithTemplate")
    @Operation(summary = "Sending mail with template.")
    @ApiResponse(responseCode = "200", description = "Mail with template has been sent.")
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
