package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;
import java.util.List;
/**
 * Интерфейс AdsMapper представляющий преобразование Ads
 */
@Mapper(uses = AdsMapper.class)
public interface AdsMapper {
    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);
    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "image", source = "adImage")
    AdDto toDtoAd(Ad ad);

    @Mapping(target = "author", source = "user.id")
    List<AdDto> toDto(List<Ad> adMeList);
}
