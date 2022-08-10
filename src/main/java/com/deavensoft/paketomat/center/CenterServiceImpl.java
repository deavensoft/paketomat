package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;
    private final EmailService emailService;
    @Autowired
    public CenterServiceImpl(@Qualifier("center") CenterRepository centerRepository,EmailService emailService) {
        this.centerRepository = centerRepository;
        this.emailService=emailService;
    }


    public List<Package> getAllPackages() {
        return centerRepository.findAll();
    }

    public void save(Package p) {
        centerRepository.save(p);
    }

    public Optional<Package> findPackageById(Long id) {
        return centerRepository.findById(id);
    }

    public void deletePackageById(Long id) {
        centerRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        centerRepository.deleteAll();
    }

    @Override
    public void updateStatus(Long code, Status status) {
        List<Package> packages = centerRepository.findAll();
        for (Package p : packages) {
            if(p.getCode().equals(code)){
                p.setStatus(status);
                LocalDateTime date = LocalDateTime.now();
                p.setDate(date);
                centerRepository.save(p);
            }
        }
    }
    public void payment(Long id, Paid paid)
    {
        List<Package> packages = centerRepository.findAll();
        for (Package p : packages) {
            if(p.getId().equals(id)){
                p.setPaid(paid);
                sendMailToUser(p.getUser().getEmail(),paid);
                centerRepository.save(p);
            }
        }


    }

    public void sendMailToUser(String email, Paid p) {
        EmailDetails emailSender = new EmailDetails();
        emailSender.setRecipient(email);
        if (Paid.PAID == p) {
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be picked up and the code is" +" "+
                    generateCode());
        } else if (Paid.NOT_PAID == p) {
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be paid");
        } else if (Paid.UN_SUCESSFULL == p) {
            emailSender.setMsgBody("Your package is in the paketomat, the payment was unsuccesfull, try again to pay for the package");
        }
        emailSender.setAttachment("");
        emailSender.setSubject("Package arrived in the paketomat");
        Map<String, Object> model = new HashMap<>();
        model.put("msgBody", emailSender.getMsgBody());
        emailService.sendMailWithTemplate(emailSender, model);
        log.info("E-Mail is sent to the end user");

    }

    public String generateCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(10000);
        String formatted = String.format("%04d", num);
        log.info("Code is generated for picking up the package");
        return formatted;


    }
}
