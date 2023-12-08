package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mappings({
            @Mapping(source = "userImage", target = "image"),
            @Mapping(target = "userName", ignore = true),
            @Mapping(target = "userName", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "countAd", ignore = true),
            @Mapping(target = "adList", ignore = true),
            @Mapping(target = "commentList", ignore = true)})
//    Возможно можно убрать все или несколько игноров и изменить источник у листов указывая через точку откуда берется источник
//    (возможно где-то и цель надо так сделать)
    UserDto toDto(User user);
}
