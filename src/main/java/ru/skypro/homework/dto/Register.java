package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "регистрация пользователя")
public class Register {
    @Schema(description = "логин")
    @NotBlank
    @Size(min = 4,max = 32)
    private String username;
    @Schema(description = "пароль")
    @NotBlank
    @Size(min = 8,max = 16)
    private String password;
    @Schema(description = "имя пользователя")
    @NotBlank
    @Size(min = 2,max = 16)
    private String firstName;
    @Schema(description = "фамилия пользователя")
    @NotBlank
    @Size(min = 2,max = 16)
    private String lastName;
    @Schema(description = "телефон пользователя")
    @Pattern(regexp ="\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
    @Schema(description = "роль пользователя")
    @NotBlank
    private Role role;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }
}
