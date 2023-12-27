package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.NewPasswordMapper;
import ru.skypro.homework.mapper.UpdateUserMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
@EqualsAndHashCode
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    /**
     * Редактирование данных пользователя
     * @param updateUser имя, фамилия, телефон
     * @param authentication данные аутентификации
     * @return UpdateUser
     */
    @Override
    public UpdateUser update(UpdateUser updateUser, Authentication authentication) {
        User user = findUserByUsername(authentication);
        UpdateUserMapper.INSTANCE.toModel(updateUser, user);
        userRepository.save(user);
        return UpdateUserMapper.INSTANCE.toDTO(user);
    }

    /**
     * Изменение пароля пользователя
     * @param newPassword старый и новый пароль
     * @param authentication данные аутентификации
     * @return NewPassword
     */
    @Override
    public NewPassword setPassword(NewPassword newPassword, Authentication authentication) {
        User user = findUserByUsername(authentication);
        String currentPassword = user.getPassword();
        if (encoder.matches(newPassword.getCurrentPassword(), currentPassword)) {
            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
            NewPasswordMapper.INSTANCE.toDto(user);
            return newPassword;
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Получение информации о зарегистрированном пользователе
     * @param authentication данные аутентификации
     * @return UserDto
     */
    @Override
    public UserDto getUserDto(Authentication authentication) {
        User user = findUserByUsername(authentication);
        return UserMapper.INSTANCE.toDto(user);
    }

    /**
     * Обновление аватара пользователя
     * @param image аватар пользователя
     * @param authentication данные аутентификации
     * @param userName login пользователя
     */
    @Override
    public void updateImage(MultipartFile image, Authentication authentication, String userName) {
        User user = findUserByUsername(authentication);
        String dir = System.getProperty("user.dir") + "/" + "avatars";
        try {
            Files.createDirectories(Path.of(dir));
            String fileName = String.format("avatar%s.%s", user.getEmail(),
                    StringUtils.getFilenameExtension(image.getOriginalFilename()));
            image.transferTo(new File(dir + "/" + fileName));
            user.setUserImage("/users/get/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userRepository.save(user);
    }

    /**
     * Проверка пользователя в БД
     * @param authentication данные аутентификации
     * @return User
     */
    @Override
    public User findUserByUsername(Authentication authentication) {
        return userRepository.findByUserName(authentication.getName());
    }

    /**
     * Проверка прав для изменения, удаления
     * @param userName login пользователя
     * @param authentication данные аутентификации
     * @return boolean
     */
    @Override
    public boolean checkUserRole(String userName, Authentication authentication) {
        User user = findUserByUsername(authentication);
        return userName.equals(authentication.getName()) || user.getRole() == Role.ADMIN;
    }

    @Override
    public byte[] getUserImage(String filename) {
        try {
            return Files.readAllBytes(Paths.get(System.getProperty("user.dir")
                    + "/"
                    + "avatars"
                    + "/"
                    + filename));
        } catch (IOException e) {
            log.error("ошибка в названии image Аватара" + filename);
            throw new RuntimeException(e);
        }
    }


}
