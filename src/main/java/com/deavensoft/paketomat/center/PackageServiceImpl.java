package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.deavensoft.paketomat.generate.Generator.generateCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {
    private final PackageRepository packageRepository;
    private final EmailService emailService;


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
    public void updateStatus(Long id, Status status) {
        Optional<Package> p = packageRepository.findById(id);
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
            String code = sendMailToUser(p.get().getUser().getEmail(), paid);
            updateCode(id, code);
            packageRepository.save(p.get());
        }
    }

    public void updateCode(Long id, String code) {
        Optional<Package> p = packageRepository.findById(id);
        if (p.isPresent()) {
            p.get().setCode(code);
            packageRepository.save(p.get());
        }
    }

    public String sendMailToUser(String email, Paid p) {
        String code="";
        EmailDetails emailSender = new EmailDetails();
        emailSender.setRecipient(email);
        if (Paid.PAID == p) {
            code = generateCode();
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be picked up, the code is" + " " +
                    code);
        } else if (Paid.NOT_PAID == p) {
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be paid");
        } else if (Paid.UNSUCCESSFUL == p) {
            emailSender.setMsgBody("Your package is in the paketomat, the payment was unsuccessful, try again to pay for the package");
        }
        emailSender.setAttachment("");
        emailSender.setSubject("Package arrived in the paketomat");
        Map<String, Object> model = new HashMap<>();
        model.put("msgBody", emailSender.getMsgBody());
        emailService.sendMailWithTemplate(emailSender, model);
        log.info("E-Mail is sent to the end user");
        return code;
    }



    public Optional<Package> findPackageByCode(String code) {
        return packageRepository.findPackageByCode(code);
    }
}

