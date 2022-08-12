package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.center.PackageService;
import com.deavensoft.paketomat.center.model.*;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import com.deavensoft.paketomat.exceptions.*;
import com.deavensoft.paketomat.paketomats.PaketomatService;
import com.deavensoft.paketomat.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class DispatcherServiceImpl implements DispatcherService {

    private final DispatcherRepository dispatcherRepository;
    @Value("${external.api.distance.url}")
    private String url;
    @Value("${external.api.distance.header.host.name}")
    private String hostName;
    @Value("${external.api.distance.header.host.value}")
    private String hostValue;
    @Value("${external.api.distance.header.key.name}")
    private String keyName;
    @Value("${external.api.distance.header.key.value}")
    private String keyValue;
    private final UserService userService;
    private final EmailService emailService;
    private final PackageService packageService;
    private final PaketomatService paketomatService;

    @Value("${paketomat.min-population}")
    private int minPopulation;

    @Value("${dispatcher.distance}")
    private String haversine;

    @Value("${paketomat.size-of-paketomat}")
    private int sizeOfPaketomat;

    public List<Dispatcher> findAllDispatchers() {
        return dispatcherRepository.findAll();
    }

    public void saveDispatcher(Dispatcher newDispatcher) {
        dispatcherRepository.save(newDispatcher);
    }

    public Optional<Dispatcher> findDispatcherById(Long id) {
        return dispatcherRepository.findById(id);
    }

    public void deleteDispatcherById(Long id) {
        dispatcherRepository.deleteById(id);
    }

    @Override
    public void delieverPackage(Package newPackage) throws PaketomatException, UnsupportedEncodingException {
        Optional<User> user = userService.findUserById(newPackage.getUser().getId());
        if (user.isPresent()) {
            if(!user.get().getAddress().contains(",")){
                throw new BadFormattingException("User address does not have right format", HttpStatus.INTERNAL_SERVER_ERROR, 500);
            }
            String[] parts = user.get().getAddress().split(",", 2);
            String cityUser = parts[1];
            findNearestCity(cityUser, newPackage);
            sendMailToCourier();
        } else
            throw new NoSuchUserException("There is no user with id " + newPackage.getUser().getId(), HttpStatus.OK, 200);
    }

    public void findNearestCity(String address, Package newPackage) throws PaketomatException, UnsupportedEncodingException {
        City city = null;
        double minDistance = 1000.0;
        double distance;
        List<City> cities = new ArrayList<>(Center.cities);
        while (true) {
            if (cities.isEmpty()) {
                throw new NoSpaceAvailableException("There is no space available in paketomats", HttpStatus.OK, 200);
            }
            for (City c : cities) {
                if (c.getPopulation() > minPopulation) {
                    distance = findDistance(c.getName(), address);
                    if (distance < minDistance) {
                        minDistance = distance;
                        city = c;
                    }
                }
            }
            if (city != null && checkIfAvailable(newPackage, city))
                break;
            cities.remove(city);
            minDistance = 1000.0;
        }
    }

    @Override
    public double findDistance(String cityPaketomat, String cityReciever) throws UnsupportedEncodingException {
        if (cityReciever.contentEquals(cityPaketomat)) {
            return 0.0;
        }
        String newUrl = url.replace("{cityReciever}", cityReciever);
        String newURL = newUrl.replace("{cityPaketomat}", cityPaketomat);
        UriTemplateHandler skipVariablePlaceHolderUriTemplateHandler = createHandler();
        URLEncoder.encode(newURL, StandardCharsets.UTF_8.toString());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(skipVariablePlaceHolderUriTemplateHandler);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set(hostName, hostValue);
        headers.set(keyName, keyValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String returnOfTemplate = restTemplate.exchange(
                newURL, HttpMethod.GET, entity, String.class).getBody();
        if(returnOfTemplate == null){
            throw new NullPointerException();
        }
        return calculateDistance(returnOfTemplate);
    }

    public double calculateDistance(String text){
        if (!text.contains(haversine))
            return 0.0;
        String part = text.substring(text.indexOf(haversine), text.indexOf("greatCircle") - 2);
        String[] parts = part.split(":", 2);
        String distance = parts[1];
        try {
            return Double.parseDouble(distance);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public boolean checkIfAvailable(Package newPackage, City city) {
        for (Paketomat paketomat : paketomatService.getAllPaketomats()) {
            if (city.getId().equals(paketomat.getCity().getId()) && paketomat.getPackages().size() < sizeOfPaketomat) {
                paketomat.reserveSlot(newPackage);
                newPackage.setPaketomat(paketomat);
                packageService.updateStatus(newPackage.getCode(), Status.TO_DISPATCH);
                log.info("Slot is reserved for package in paketomat in city " + city.getName());
                return true;
            }
        }
        return false;
    }

    public void sendMailToCourier() {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("courierpaketomat@gmail.com");
        emailDetails.setMsgBody("Hello, new package should be delivered");
        emailDetails.setSubject("Package delivering");
        Map<String, Object> model = new HashMap<>();
        model.put("msgBody", emailDetails.getMsgBody());
        emailService.sendMailWithTemplate(emailDetails, model);
        log.info("Mail is sent to courier");
    }

    private UriTemplateHandler createHandler(){
        return  new UriTemplateHandler() {
            @NotNull
            @Override
            public URI expand(String uriTemplate, Object... uriVariables) {
                return retrieveURI(uriTemplate);
            }

            @NotNull
            @Override
            public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
                return retrieveURI(uriTemplate);
            }

            private URI retrieveURI(String uriTemplate) {
                return UriComponentsBuilder.fromUriString(uriTemplate).build().toUri();
            }
        };
    }
}
