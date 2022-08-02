package com.deavensoft.paketomat.mapper;

import com.deavensoft.paketomat.center.dto.PackageDTO;
import com.deavensoft.paketomat.center.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    PackageMapper INSTANCE = Mappers.getMapper(PackageMapper.class);
    @Mapping(target="id_dto", source= "id")
    PackageDTO packageToPackageDTO(Package p);

    List<PackageDTO> packagesToPackageDTO(List<Package> packages);

    Package packageDTOToPackage(PackageDTO packageDTO);
}