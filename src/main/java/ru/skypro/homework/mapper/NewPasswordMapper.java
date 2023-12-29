package ru.skypro.homework.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.entity.User;
/**
 * Интерфейс NewPasswordMapper представляющий преобразование NewPassword
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewPasswordMapper.class})
public interface NewPasswordMapper {

    NewPasswordMapper INSTANCE = Mappers.getMapper(NewPasswordMapper.class);
    @Mapping(source = "user.password", target = "currentPassword")
    NewPassword toDto(User user);

    @Mapping(source = "newPassword.newPassword", target = "password")
    User toModel(NewPassword newPassword);
    @InheritConfiguration
    void updateModel(NewPassword newPassword, @MappingTarget User user);
}
