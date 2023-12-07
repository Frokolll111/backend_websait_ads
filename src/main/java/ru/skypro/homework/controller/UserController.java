package ru.skypro.homework.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestBody NewPassword updatePasswordDTO){
        return ResponseEntity.ok("Пароль изменен");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(new UserDto());
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUserDTO){
        return ResponseEntity.ok(new UpdateUser());
    }

    @PatchMapping("/me/image")
    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image) {
        return ResponseEntity.ok("Изображение изменено");
    }

}
