package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateAdMapper {

    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    Ad createOrUpdateAdToAd(CreateOrUpdateAd createOrUpdateAd);

    CreateOrUpdateAd adToCreateOrUpdateAd(Ad ad);
}
