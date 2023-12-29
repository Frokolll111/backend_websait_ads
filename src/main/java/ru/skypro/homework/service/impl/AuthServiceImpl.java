package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.MyUserDetailsService;
/**
 * Класс AuthServiceImpl - это класс для аутентификации
 * Он предоставляет интерфейс регистрации и авторизации
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsService manager;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(MyUserDetailsService manager,
                           PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.encoder = passwordEncoder;
    }

    /**
     * Авторизация пользователя
     * @param userName login пользователя
     * @param password пароль
     * @return boolean
     */
    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    /**
     * Регистрация пользователя
     * @param register данные для регистрации
     * @return boolean
     */
    @Override
    public boolean register(Register register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())
                        .build());
        manager.createUser(register);
        return true;
    }
}

