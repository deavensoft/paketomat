package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.courier.CourierModel;
import com.deavensoft.paketomat.courier.dto.CourierDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);

    @Mapping(target = "id", source = "id")
    CourierDTO courierToCourierDTO(CourierModel courierModel);

    List<CourierDTO> couriersToCourierDTO(List<CourierModel> courierModels);

    CourierModel courierDTOToCourier(CourierDTO courierDTO);

}
