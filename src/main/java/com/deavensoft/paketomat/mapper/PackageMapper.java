package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.center.dto.PackageDTO;
import com.deavensoft.paketomat.center.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    PackageMapper INSTANCE = Mappers.getMapper(PackageMapper.class);
    @Mapping(target="idDto", source= "id")
    PackageDTO packageToPackageDTO(Package p);

    Package packageDTOToPackage(PackageDTO packageDTO);
}