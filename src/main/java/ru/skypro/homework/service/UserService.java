package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
/**
 * Интерфейс UserService - это интерфейс для работы с UserServiceImpl
 */
@Service
public interface UserService {

    UpdateUser update(UpdateUser updateUser, Authentication authentication);

    NewPassword setPassword(NewPassword newPasswordDto, Authentication authentication);

    UserDto getUserDto(Authentication authentication);

    void updateImage(MultipartFile image, Authentication authentication, String userName);

    User findUserByUsername(Authentication authentication);

    boolean checkUserRole(String currentAuthor, Authentication authentication);

    byte[] getUserImage(String filename);
}
