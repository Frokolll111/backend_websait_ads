package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Пользователи", description = "контроллер для работы с пользователями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Обновление пароля",
            responses = {@ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true)))})
    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword updatePassword, Authentication authentication){
        return ResponseEntity.ok(userService.setPassword(updatePassword, authentication));
    }

    @Operation(
            summary = "Получение информации об авторизованным пользователем",
            responses = {@ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true)))})
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserDto(authentication));
    }
    @Operation(
            summary = "Обновление информации об авторизованном пользователе",
            responses = {@ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                            schema = @Schema(implementation = UpdateUser.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true)))})
    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser,
                                                 Authentication authentication){
        return ResponseEntity.ok(userService.update(updateUser, authentication));
    }
    @Operation(
            summary = "Обновление аватара авторизованного пользователя",
            responses = {@ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true)))})
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image,
                                                  Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        userService.updateImage(image, authentication, userName);
        return ResponseEntity.ok().build();
    }


}
