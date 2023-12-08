package ru.skypro.homework.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class MapperUtil {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}
