package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.RegisterMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserRepository userRepository, UserServiceImpl userService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.encoder = encoder;
    }


    @Override
    public boolean login(String userName, String password) {
        if (!userRepository.findByUserName(userName).getUserName().isEmpty()) {
            return false;
        }
        UserDetails userDetails = userService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }


    @Override
    public boolean register(Register register) {
        if (!userRepository.findByUserName(register.getUsername()).getUserName().isEmpty()) {
            return false;
        }
        User user = RegisterMapper.INSTANCE.toModel(register);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

}
