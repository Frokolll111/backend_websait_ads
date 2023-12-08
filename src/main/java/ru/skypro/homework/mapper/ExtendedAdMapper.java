package ru.skypro.homework.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;

@Mapper
public interface ExtendedAdMapper {

    ExtendedAdMapper INSTANCE = Mappers.getMapper(ExtendedAdMapper.class);

    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "adImage", target = "image")
    @Mapping(source = "user.phone", target = "phone")
    ExtendedAd adToExtendedAd(Ad ad);

    @InheritInverseConfiguration
    Ad extendedAdToAd(ExtendedAd extendedAd);
}
