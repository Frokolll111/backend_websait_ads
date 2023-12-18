package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegisterMapper {

    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    @Mapping(source = "registerDto.username", target = "userName")
    @Mapping(source = "registerDto.password", target = "password")
    @Mapping(source = "registerDto.firstName", target = "firstName")
    @Mapping(source = "registerDto.lastName", target = "lastName")
    @Mapping(source = "registerDto.phone", target = "phone")
    @Mapping(source = "registerDto.role", target = "role")
    @Mapping(source = "registerDto.username", target = "email")
    User toModel(Register registerDto);
    Register toDTO(User user);
}
