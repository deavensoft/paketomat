package com.deavensoft.paketomat.email;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SimpleMailMessage mailMessage;

    @Autowired
    Configuration fmConfiguration;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails details) {

        try {

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());



            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());


            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    "Thymeleaf.html", file);

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        catch (MessagingException e) {

            return "Error while sending mail!!!";
        }
    }

    @Override
    public int sendMailWithTemplate(EmailDetails details, Map<String, Object> modelData) {
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(details.getSubject());
            mimeMessageHelper.setTo(details.getRecipient());
            details.setMsgBody(geContentFromTemplate(modelData));
            mimeMessageHelper.setText(details.getMsgBody(), true);

            try {

                if (!details.getAttachment().isEmpty()) {
                    FileSystemResource file
                            = new FileSystemResource(
                            new File(details.getAttachment()));

                    mimeMessageHelper.addAttachment(
                            details.getAttachment(), file);
                }
            } catch (NullPointerException e){
                log.info("Email sent without attachment.");
            }
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public String geContentFromTemplate(Map< String, Object > model)     {
        StringBuilder content = new StringBuilder();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.flth.html"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
