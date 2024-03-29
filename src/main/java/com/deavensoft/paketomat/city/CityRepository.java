package com.deavensoft.paketomat.city;

import com.deavensoft.paketomat.center.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("city")
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findCityByName(String name);
}
