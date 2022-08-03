package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.courier.Courier;
import com.deavensoft.paketomat.courier.dto.CourierDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);

    @Mapping(target = "id", source = "id")
    CourierDTO courierToCourierDTO(Courier courierModel);

    Courier courierDTOToCourier(CourierDTO courierDTO);

}
