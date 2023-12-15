package ru.skypro.homework.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "сохранение и обновление объявления")
public class CreateOrUpdateAd {
    @Schema(description = "Заголовок объявления")
    @NotBlank
    @Size(min = 4,max = 32)
    private String title;
    @Schema(description = "Цена объявления")
    @NotBlank
    @Size(min = 0,max = 10000000)
    private Integer price;
    @Schema(description = "Описание объявления")
    @NotBlank
    @Size(min = 8,max = 64)
    private String description;
}
