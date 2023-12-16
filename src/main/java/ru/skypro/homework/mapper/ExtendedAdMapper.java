package ru.skypro.homework.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExtendedAdMapper {

    ExtendedAdMapper INSTANCE = Mappers.getMapper(ExtendedAdMapper.class);
    @Mapping(source = "ad.pk", target = "pk")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "ad.description", target = "description")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "ad.adImage", target = "image")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "ad.price", target = "price")
    @Mapping(source = "ad.title", target = "title")

    ExtendedAd toDto(Ad ad, User user);

}
