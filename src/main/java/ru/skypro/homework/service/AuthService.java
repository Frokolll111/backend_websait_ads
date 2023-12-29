package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;
/**
 * Интерфейс AuthService - это интерфейс для работы с AuthServiceImpl
 */
public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);
}
