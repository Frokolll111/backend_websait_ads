package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.util.List;

//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//
//    @Autowired
//    public UserService(UserRepository userRepository, UserMapper userMapper) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//    }
//
//    public UserDto convertToDTO(User user) {
//        return userMapper.userToUserDto(user);
//    }
//
//    public User convertToEntity(UserDto userDTO) {
//        return userMapper.userDtoToUser(userDTO);
//    }
//
//    public List<UserDto> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return userMapper.usersToUserDtos(users);
//    }
//
//    public UserDto getUserById(Integer userId) {
//        return userRepository.findById(userId)
//                .map(userMapper::userToUserDto)
//                .orElse(null);
//    }
//
//    public void addUser(UserDto userDTO) {
//        User user = userMapper.userDtoToUser(userDTO);
//        userRepository.save(user);
//    }
//
//    public void updateUser(Integer userId, UserDto userDTO) {
//        userRepository.findById(userId).ifPresent(existingUser -> {
//            User updatedUser = userMapper.userDtoToUser(userDTO);
//            updatedUser.setId(userId);
//            userRepository.save(updatedUser);
//        });
//    }
//
//    public void deleteUser(Integer userId) {
//        userRepository.deleteById(userId);
//    }
//}
