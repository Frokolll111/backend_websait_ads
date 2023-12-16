package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Пользователи", description = "контроллер для работы с пользователями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword updatePasswordDTO, Authentication authentication){
        authentication.getName();
        return ResponseEntity.ok().build();
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
