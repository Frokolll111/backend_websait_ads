package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.User;

@Mapper
public interface UpdateUserMapper {
    UpdateUserMapper INSTANCE = Mappers.getMapper(UpdateUserMapper.class);

//    @Mappings({
//            @Mapping(target = "user.id", ignore = true),
//            @Mapping(target = "user.role", ignore = true),
//            @Mapping(target = "user.email", ignore = true),
//            @Mapping(target = "user.image", ignore = true),
//            @Mapping(target = "user.password", ignore = true),
//            @Mapping(target = "user.countAd", ignore = true),
//            @Mapping(target = "user.adList", ignore = true),
//            @Mapping(target = "user.commentList", ignore = true)})
//    Возможно можно убрать все или несколько игноров и изменить источник у листов указывая через точку откуда берется источник
//    (возможно где-то и цель надо так сделать)
    User toEntity(UpdateUser updateUser, @MappingTarget User user);
    UpdateUser toDTO(User user);
}
