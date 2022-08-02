package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.dispatcher.Dispatcher;
import com.deavensoft.paketomat.dispatcher.dto.DispatcherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DispatcherMapper {

    DispatcherMapper INSTANCE = Mappers.getMapper(DispatcherMapper.class);

    @Mapping(target = "id", source = "id")
    DispatcherDTO dispatcherToDispatcherDTO(Dispatcher dispatcherModel);

    List<DispatcherDTO> dispatchersToDispatcherDTO(List<Dispatcher> dispatcherModels);

    Dispatcher dispatcherDTOToDispatcher(DispatcherDTO dispatcherDTO);
}
