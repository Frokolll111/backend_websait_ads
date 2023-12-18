package ru.skypro.homework.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "список комментариев")
public class Comments {
    @Schema(description = "количество комментариев")
    private Integer count;
    @Schema(description = "комментарии")
    private List<CommentDto> result;


    public Comments(List<CommentDto> result) {
        this.result = result;
        this.count = result.size();
    }
}

