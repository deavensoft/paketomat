package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.dto.CityDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);
    @Mapping(target="geonameid", source= "id")
    CityDto cityToCityDto(City city);

    List<CityDto> citiesToCityDto(List<City> c);

    City cityDtoToCity(CityDto cityDto);
}
