package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.dto.CitiesDto;
import com.deavensoft.paketomat.city.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CitiesMapper {


   CitiesMapper INSTANCE = Mappers.getMapper(CitiesMapper.class);

    CitiesDto cityDtoToCity(City city);

    City cityDtoToCity(CityDto cityDto);
}
