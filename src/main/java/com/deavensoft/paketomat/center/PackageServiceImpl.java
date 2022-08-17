package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import com.deavensoft.paketomat.exceptions.NoSuchPackageException;
import com.deavensoft.paketomat.exceptions.NoSuchStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

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

    public void payment(Long id, Paid paid) throws NoSuchPackageException {
        Optional<Package> p = packageRepository.findById(id);
        if (p.isPresent()) {
            p.get().setPaid(paid);
            String code = sendMailToUser(p.get().getUser().getEmail(), paid);
            updateCode(id, code);
            packageRepository.save(p.get());
        }
        else {
            throw new NoSuchPackageException("There is no package with specified id", HttpStatus.OK, 200);
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

    @Override
    public List<Package> getPackageByStatus(int status) throws NoSuchStatusException {
        List<Package> packages = packageRepository.findAll();
        List<Package> packageList = new ArrayList<>();

        for(Package p: packages){
            if(p.getStatus() == getStatus(status)){
                packageList.add(p);
            }
        }
        return packageList;
    }
    private Status getStatus(int status) throws NoSuchStatusException {
        switch (status){
            case 1: return Status.NEW;
            case 2: return Status.TO_DISPATCH;
            case 3: return Status.IN_PAKETOMAT;
            case 4: return Status.DELIVERED;
            case 5: return Status.RETURNED;
            default:
                throw new NoSuchStatusException("There is no such status", HttpStatus.OK, 200);

        }
    }
}

