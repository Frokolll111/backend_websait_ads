package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {

    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    @Mappings({
            @Mapping(target = "author", source = "user.id"),
            @Mapping(target = "image", source = "adImage"),
            @Mapping(target = "pk", source = "pk"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "title", source = "title")
    })
    AdDto adToAdDto(Ad ad);

    @Mappings({
            @Mapping(target = "user.id", source = "author"),
            @Mapping(target = "adImage", source = "image"),
            @Mapping(target = "pk", source = "pk"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "title", source = "title")
    })
    Ad adDtoToAd(AdDto adDto);

    List<AdDto> adsToAdDtos(List<Ad> ads);

    List<Ad> adDtosToAds(List<AdDto> adDtos);
}