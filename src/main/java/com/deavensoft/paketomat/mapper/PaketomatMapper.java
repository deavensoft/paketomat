package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.paketomats.dto.PaketomatDTO;
import com.deavensoft.paketomat.center.model.Paketomat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaketomatMapper {

    PaketomatMapper INSTANCE = Mappers.getMapper(PaketomatMapper.class);

    @Mapping(target = "id", source = "id")
    PaketomatDTO paketomatToPaketomatDTO(Paketomat paketomat);

    Paketomat paketomatDTOToPaketomat(PaketomatDTO paketomatDTO);

}
