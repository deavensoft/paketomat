package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.center.PackageService;
import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.city.CityService;
import com.deavensoft.paketomat.dispatcher.DispatcherService;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import com.deavensoft.paketomat.generate.Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static com.deavensoft.paketomat.generate.Generator.generateCode;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final PackageService packageService;
    private final CityService cityService;
    private final DispatcherService dispatcherService;
    private final EmailService emailService;


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
    public List<Package> getPackagesForCourier(String city) throws PaketomatException, UnsupportedEncodingException {
        return deliverPackageInPaketomat(filterPackagesToDispatch(getPackagesToDispatch(), findCitiesInRadius(city)));
    }

    @Scheduled(cron = "${cron[0].schedule}")
    @Override
    public List<Package> getNotPickedUpPackages() {
        List<Package> packagesInPaketomat = packageService.getAllPackages();
        List<Package> packagesToReturn = new ArrayList<>();
        LocalDateTime currentTime;
        for (Package p : packagesInPaketomat) {
            if (p.getStatus().equals(Status.IN_PAKETOMAT)) {
                currentTime = LocalDateTime.now();
                currentTime = currentTime.minusDays(5);
                if (currentTime.isAfter(p.getDate())) {
                    packagesToReturn.add(p);
                    packageService.updateStatus(p.getId(), Status.RETURNED);
                }
            }
        }
        return packagesToReturn;
    }

    @Override
    public List<Package> returnNotPickedUpPackages() {
        List<Package> packages = packageService.getAllPackages();
        List<Package> packagesToReturn = new ArrayList<>();
        for (Package p : packages) {
            if (p.getStatus().equals(Status.RETURNED)) {
                packagesToReturn.add(p);
                p.getPaketomat().freeBox(p);
                packageService.updateStatus(p.getId(), Status.TO_DISPATCH);
            }
        }
        return packagesToReturn;
    }

    public List<Package> getPackagesToDispatch() {
        List<Package> packageList = packageService.getAllPackages();
        List<Package> packagesToDispatch = new ArrayList<>();

        for (Package p : packageList) {
            if (p.getStatus() == Status.TO_DISPATCH) {
                packagesToDispatch.add(p);
            }
        }
        log.info("List with packages TO_DISPATCH is made");
        return packagesToDispatch;
    }

    public List<City> findCitiesInRadius(String city) throws PaketomatException, UnsupportedEncodingException {
        double maxDistance = 100.0;
        double distance;
        List<City> citiesList = cityService.getAllCities();
        List<City> citiesToDispatch = new ArrayList<>();

        for (City c : citiesList) {
            if (c.getPopulation() >= 10000) {
                distance = dispatcherService.findDistance(city, c.getName());
                if (distance <= maxDistance) {
                    citiesToDispatch.add(c);
                }
            }
        }
        log.info("List with cities in 100km radius from city " + city + " is made");
        return citiesToDispatch;
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
            packageService.updateStatus(p.getId(), Status.IN_PAKETOMAT);
            checkIfThePackageIsPaid(p);
        }
        log.info("Packages are in paketomat and are ready for delivery");
        return packages;
    }

    public void sendEMailToUser(String email, Paid p) {
        EmailDetails emailSender = new EmailDetails();
        emailSender.setRecipient(email);
        if (Paid.PAID == p) {
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be picked up and the code is" + " " +
                    generateCode());
        } else if (Paid.NOT_PAID == p) {
            emailSender.setMsgBody("Your package is in the paketomat and is ready to be paid");
        } else if (Paid.UNSUCCESSFUL == p) {
            emailSender.setMsgBody("Your package is in the paketomat, the payment were unsuccessful, try again to pay for the package");
        }
        emailSender.setAttachment("");
        emailSender.setSubject("Package arrived in the paketomat");
        Map<String, Object> model = new HashMap<>();
        model.put("msgBody", emailSender.getMsgBody());
        emailService.sendMailWithTemplate(emailSender, model);
        log.info("E-Mail is sent to the end user");

    }

    public void checkIfThePackageIsPaid(Package p) {
        if (p.getPaid() == Paid.PAID) {
            sendEMailToUser(p.getUser().getEmail(), Paid.PAID);
        } else if (p.getPaid() == Paid.NOT_PAID) {
            sendEMailToUser(p.getUser().getEmail(), Paid.NOT_PAID);
        } else if (p.getPaid() == Paid.UNSUCCESSFUL) {
            sendEMailToUser(p.getUser().getEmail(), Paid.UNSUCCESSFUL);
        }
    }

    public void exportToCSV(HttpServletResponse response, String city) throws PaketomatException, IOException {
        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NewPackagesToBeDeliveredInCity.csv";
        response.setHeader(headerKey, headerValue);

        List<Package> listPackages = getPackagesForCourier(city);

        try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.EXCEL)) {
            for (Package p : listPackages) {
                csvPrinter.printRecord("ID of the package","Serial Code from Paketomat","Status","E-Mail","Name of the User","Paid","PIN Code for the paketomat");
                csvPrinter.printRecord(p.getId(),p.getPaketomat().getAddr(), p.getStatus(), p.getUser().getEmail(),p.getUser().getName(),p.getPaid(),p.getCode());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }

    }

    @Override
    public void exportOutdatedPackagesToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String fileName = "notPickedUpPackages.csv";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=" + fileName;

        response.setHeader(headerKey, headerValue);

        List<Package> packages = getNotPickedUpPackages();

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Id", "Status", "User id", "Paketomat id", "Code", "Center", "Date"};
        String[] nameMapping = {"id", "status", "user_id", "paketomat_id", "code", "center_id", "date"};

        csvBeanWriter.writeHeader(csvHeader);

        for (Package p : packages) {
            csvBeanWriter.write(p, nameMapping);
        }

        csvBeanWriter.close();
    }
}
