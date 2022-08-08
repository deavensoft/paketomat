package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.dispatcher.Dispatcher;
import com.deavensoft.paketomat.dispatcher.dto.DispatcherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DispatcherMapper {

    DispatcherMapper INSTANCE = Mappers.getMapper(DispatcherMapper.class);

    DispatcherDTO dispatcherToDispatcherDTO(Dispatcher dispatcherModel);

    Dispatcher dispatcherDTOToDispatcher(DispatcherDTO dispatcherDTO);
}
