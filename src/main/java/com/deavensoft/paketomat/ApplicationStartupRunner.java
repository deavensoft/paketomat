package com.deavensoft.paketomat;

import com.deavensoft.paketomat.center.model.Center;
import com.deavensoft.paketomat.center.model.Paketomat;
import com.deavensoft.paketomat.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationStartupRunner implements CommandLineRunner {
    private final CityService cityService;
    @Override
    public void run(String... args) throws Exception {
        Center.cities = cityService.getAllCities();

    }
}
