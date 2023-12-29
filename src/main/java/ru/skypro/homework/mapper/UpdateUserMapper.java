package ru.skypro.homework.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UpdateUserMapper {

    UpdateUserMapper INSTANCE = Mappers.getMapper(UpdateUserMapper.class);
    User toModel(UpdateUser updateUser, @MappingTarget User user);
    UpdateUser toDTO(User user);
}
