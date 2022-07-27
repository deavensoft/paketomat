package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.dto.CityDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = CitiesMapper.class)
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);
    @Mapping(target="geonameid", source= "id")
    CityDto cityToCityDto(City city);

    City cityDtoToCity(CityDto cityDto);
}
