package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "список объявлений")
public class Ads {
    @Schema(description = "количество объявлений")
    private Integer count;
    @Schema(description = "список объявлений")
    private List<AdDto> results;
}
