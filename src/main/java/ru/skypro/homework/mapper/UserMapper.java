package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
/**
 * Интерфейс UserMapper представляющий преобразование UserDto
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "user.adList", ignore = true)
    @Mapping(target = "image", source = "userImage")
    UserDto toDto(User user);
}
