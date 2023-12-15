package ru.skypro.homework.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "Объявление")
public class AdDto {
    @Schema(description = "id автора объявления")
    @NotEmpty
    private Integer author;
    @Schema(description = "ссылка на картинку объявления")
    private String image;
    @Schema(description = "id объявления", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer pk;
    @Schema(description = "цена объявления")
    private Integer price;
    @Schema(description = "заголовок объявления")
    @NotBlank
    private String title;
}
