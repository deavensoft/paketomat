package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.center.CenterService;
import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.city.CityService;
import com.deavensoft.paketomat.dispatcher.DispatcherService;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import com.deavensoft.paketomat.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final CenterService centerService;
    private final CityService cityService;
    private final DispatcherService dispatcherService;
    private final EmailService emailService;
    private final UserService userService;

    public List<Courier> findAllCouriers() {
        return courierRepository.findAll();
    }

    public void saveCourier(Courier newCourier) {
        courierRepository.save(newCourier);
    }


    public Optional<Courier> getCourierById(Long id) {
        return courierRepository.findById(id);
    }

    public void deleteCourierById(Long id) {
        courierRepository.deleteById(id);
    }

    @Override
    public List<Package> getPackagesForCourier(String city) throws PaketomatException {
        return deliverPackageInPaketomat(filterPackagesToDispatch(getPackagesToDispatch(), findCitiesInRadius(city)));
    }

    public List<Package> getPackagesToDispatch() {
        List<Package> packageList = centerService.getAllPackages();
        ArrayList<Package> packagesToDispatch = new ArrayList<>();

        for (Package p : packageList) {
            if (p.getStatus().equals(Status.TO_DISPATCH)) {
                packagesToDispatch.add(p);
            }
        }
        log.info("List with packages TO_DISPATCH is made");
        return packageList;
    }

    public List<City> findCitiesInRadius(String city) throws PaketomatException {
        double maxDistance = 100.0;
        double distance;
        List<City> citiesList = cityService.getAllCities();
        ArrayList<City> citiesToDispatch = new ArrayList<>();

        for (City c : citiesList) {
            if (c.getPopulation() >= 10000) {
                distance = dispatcherService.findDistance(city, c.getName());
                if (distance <= maxDistance) {
                    citiesToDispatch.add(c);
                }
            }
        }
        log.info("List with cities in 100km radius from city " + city + " is made");
        return citiesList;
    }

    public List<Package> filterPackagesToDispatch(List<Package> packagesToDispatch, List<City> citiesToDispatch) {
        List<Package> packages = new ArrayList<>();
        for (Package p : packagesToDispatch) {
            String city = p.getPaketomat().getCity().getName();
            if (isCityInList(city, citiesToDispatch))
                packages.add(p);
        }
        log.info("Packages are filtered so courier can deliver them");
        return packages;
    }

    public boolean isCityInList(String city, List<City> citiesToDispatch) {
        for (City c : citiesToDispatch) {
            if (c.getName().equalsIgnoreCase(city.trim())) {
                return true;
            }
        }
        return false;
    }

    public List<Package> deliverPackageInPaketomat(List<Package> packages) {
        for (Package p : packages) {
            centerService.updateStatus(p.getCode(), Status.IN_PAKETOMAT);
            if (p.getPaid() == Paid.PAID)
                sendMailToUser(p.getUser().getEmail(), Paid.PAID);
            else if (p.getPaid() == Paid.NOT_PAID) {
                sendMailToUser(p.getUser().getEmail(), Paid.NOT_PAID);
            } else if (p.getPaid() == Paid.UN_SUCESSFULL) {
                sendMailToUser(p.getUser().getEmail(), Paid.UN_SUCESSFULL);

            }
        }
        log.info("Packages are in paketomat and are ready for delivery");
        return packages;
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
            emailSender.setMsgBody("Your package is in the paketomat, the payment were unsuccesfull, try again to pay for the package");
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
