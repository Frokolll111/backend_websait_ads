package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.ReportingPolicy;

import ru.skypro.homework.dto.Login;
import ru.skypro.homework.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginMapper {
    @Mapping(source = "login.username", target = "userName")
    @Mapping(source = "login.password", target = "password")
    User toModel(Login login);

}
