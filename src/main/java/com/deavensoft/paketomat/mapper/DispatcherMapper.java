package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.dispatcher.DispatcherModel;
import com.deavensoft.paketomat.dispatcher.dto.DispatcherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DispatcherMapper {

    DispatcherMapper INSTANCE = Mappers.getMapper(DispatcherMapper.class);

    @Mapping(target = "id", source = "id")
    DispatcherDTO dispatcherToDispatcherDTO(DispatcherModel dispatcherModel);

    List<DispatcherDTO> dispatchersToDispatcherDTO(List<DispatcherModel> dispatcherModels);

    DispatcherModel dispatcherDTOToDispatcher(DispatcherDTO dispatcherDTO);
}
