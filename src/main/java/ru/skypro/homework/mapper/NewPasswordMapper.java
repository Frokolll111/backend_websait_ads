package ru.skypro.homework.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.entity.User;

@Mapper
public interface NewPasswordMapper {

    NewPasswordMapper INSTANCE = Mappers.getMapper(NewPasswordMapper.class);

    @Mapping(source = "newPassword", target = "password")
    User newPasswordToUser(NewPassword newPassword);

    @InheritInverseConfiguration
    NewPassword userToNewPassword(User user);
}
