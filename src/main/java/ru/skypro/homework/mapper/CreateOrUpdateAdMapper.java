package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateOrUpdateAdMapper {

    CreateOrUpdateAdMapper INSTANCE = Mappers.getMapper(CreateOrUpdateAdMapper.class);
    CreateOrUpdateAd toDto(Ad ad, User user);
    @Mapping(target = "title", source = "createOrUpdateAd.title")
    @Mapping(target = "price", source = "createOrUpdateAd.price")
    @Mapping(target = "description", source = "createOrUpdateAd.description")
    Ad toModel(CreateOrUpdateAd createOrUpdateAd);
}
