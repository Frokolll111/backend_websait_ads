package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "userImage", target = "image")
    })
    UserDto userToUserDto(User user);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "image", target = "userImage")
    })
    User userDtoToUser(UserDto userDto);

    List<UserDto> usersToUserDtos(List<User> users);

    List<User> userDtosToUsers(List<UserDto> userDtos);

}
