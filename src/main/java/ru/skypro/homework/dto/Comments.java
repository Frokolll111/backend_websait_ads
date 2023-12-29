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
    private List<CommentDto> results;


    public Comments(List<CommentDto> results) {
        this.results = results;
        this.count = results.size();
    }
}

