package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {
    private final PackageRepository packageRepository;
    private final EmailService emailService;

    @Autowired

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public void save(Package p) {
        packageRepository.save(p);
    }

    public Optional<Package> findPackageById(Long id) {
        return packageRepository.findById(id);
    }

    public void deletePackageById(Long id) {
        packageRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        packageRepository.deleteAll();
    }

    @Override
    public void updateStatus(Long code, Status status) {
        Optional<Package> p = packageRepository.findPackageByCode(code);
        if (p.isPresent()) {
            p.get().setStatus(status);
            LocalDateTime date = LocalDateTime.now();
            p.get().setDate(date);
            packageRepository.save(p.get());
        }
    }

    public void payment(Long id, Paid paid) {
        Optional<Package> p = packageRepository.findById(id);
        if (p.isPresent()) {

            p.get().setPaid(paid);
            sendMailToUser(p.get().getUser().getEmail(), paid);
            packageRepository.save(p.get());
        }

    }

    public void sendMailToUser(String email, Paid p) {
        EmailDetails emailSender = new EmailDetails();
        emailSender.setRecipient(email);
        if (Paid.PAID == p) {
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be picked up and the code is" + " " +
                    generateCode());
        } else if (Paid.NOT_PAID == p) {
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be paid");
        } else if (Paid.UNSUCCESSFUL == p) {
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
        SecureRandom pinCodeForPaketomat = new SecureRandom();
        int codeForPaketomat = pinCodeForPaketomat.nextInt(10000);
        String formatted = String.format("%04d", codeForPaketomat);
        log.info("Code is generated for picking up the package");
        return formatted;


    }

    public Optional<Package> findPackageByCode(Long code) {
        return packageRepository.findPackageByCode(code);
    }

}
