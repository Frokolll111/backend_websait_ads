package ru.skypro.homework.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Schema(description = "Комментарии")
public class CommentDto {
    @Schema(description = "id автора комментария")
    @NotEmpty
    private Integer author;
    @Schema(description = "ссылка на аватар автора комментария")
    private String authorImage;
    @Schema(description = "имя автора комментария")
    private String authorFirstName;
    @Schema(description = "дата и время создания комментария")
    private LocalDateTime createdAt;
    @Schema(description = "id комментария", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer pk;
    @Schema(description = "текст комментария")
    private String text;
}
