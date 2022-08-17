package com.deavensoft.paketomat.city.scheduler;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.CityService;
import com.deavensoft.paketomat.city.dto.CitiesDto;
import com.deavensoft.paketomat.city.dto.CityDto;
import com.deavensoft.paketomat.mapper.CityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

@RequiredArgsConstructor
@Slf4j
public class CityIntegrationImpl implements CityIntegration {

    public static final String SUCCESSFULLY_FINISHED = "Successfully finished!";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String IMPORTING_CITIES_FINISHED = "Importing cities: FINISHED!";
    private final CityMapper cityMapper;
    private final CityService cityService;
    @Value("${external.api.city.url}")
    private String url;
    @Value("${external.api.city.header.host.name}")
    private String hostName;
    @Value("${external.api.city.header.host.value}")
    private String hostValue;
    @Value("${external.api.city.header.key.name}")
    private String keyName;
    @Value("${external.api.city.header.key.value}")
    private String keyValue;
    @Value("${external.api.city.start-page}")
    private Integer startPage;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private int currentPage;

    private int totalPages;


    private Runnable cityTask() {
        return () -> {
            if (currentPage <= totalPages) {
                final List<CityDto> citiesFromUrl = getCitiesFromUrl();
                saveCitiesIntoDatabase(citiesFromUrl);
            } else {
                executorService.shutdown();
            }
        };
    }

    private void saveCitiesIntoDatabase(List<CityDto> cities) {
        for (CityDto cityDto : cities) {
            City city = cityMapper.cityDtoToCity(cityDto);
            cityService.save(city);
        }
    }


    private List<CityDto> getCitiesFromUrl() {
        String composedUrl = url.replace("{1}", Integer.toString(currentPage));
        log.info("Url: " + composedUrl);

        currentPage++;

        try {
            CitiesDto citiesDtoo = new ObjectMapper().readValue(doRequest(composedUrl),
                    CitiesDto.class);
            return citiesDtoo.getCities();
        } catch (Exception e) {
            log.error("Data cannot be cast.");
            return Collections.emptyList();
        }
    }

    private Integer getTotalPages() {
        try {
            final String validUrl = url.replace("{1}", Integer.toString(currentPage));
            CitiesDto citiesDto = new ObjectMapper().readValue(doRequest(validUrl), CitiesDto.class);
            return citiesDto.getTotalPages();
        } catch (IOException e) {
            return -1;
        }
    }

    private String doRequest(String url) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set(hostName, hostValue);
        headers.set(keyName, keyValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String templateResponse = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class).getBody();

        log.info("TemplateResponse: " + templateResponse);

        if (templateResponse == null) {
            throw new NullPointerException();
        }

        return templateResponse;
    }

    @Override
    public String importCities() {
        totalPages = getTotalPages();
        currentPage = startPage;
        if (totalPages >= 0) {
            Runnable task = cityTask();

            try {
                if (executorService.isShutdown()) {
                    executorService = Executors.newSingleThreadScheduledExecutor();
                }
                executorService.scheduleWithFixedDelay(task, 2, 2, TimeUnit.SECONDS);
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error(SOMETHING_WENT_WRONG);
                Thread.currentThread().interrupt();
                return SOMETHING_WENT_WRONG;
            } finally {
                log.info(SUCCESSFULLY_FINISHED);
                executorService.shutdown();
            }
        }

        return IMPORTING_CITIES_FINISHED;
    }
}
