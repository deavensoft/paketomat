package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.user.dto.UserDTO;
import com.deavensoft.paketomat.center.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "id")
    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
