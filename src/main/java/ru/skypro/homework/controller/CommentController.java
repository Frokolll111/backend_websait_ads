package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Получение комментариев объявления",
            responses = {@ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = Comments.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true)))})
    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComment(@PathVariable Integer id) {
        Comments comments = commentService.getCommentsByAdId(id);
        return ResponseEntity.ok(comments);
    }

    @Operation(
            summary = "Добавление комментария к объявлению",
            responses = {@ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                            schema = @Schema(implementation = CommentDto.class)
                            )),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true)))})
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateComment createOrUpdateComment,
                                                 Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment( id,createOrUpdateComment, authentication));
    }

    @Operation(
            summary = "Удаление комментария",
            responses = {@ApiResponse(responseCode = "200",
                            description = "OK"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden"),
                    @ApiResponse(responseCode = "404",
                            description = "Not found")})
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int adId,
                                              @PathVariable int commentId,
                                              Authentication authentication) {
        commentService.deleteComment(adId, commentId,authentication);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Обновление комментария",
            responses = {@ApiResponse(responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                            schema = @Schema(implementation = CommentDto.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true)))})
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CreateOrUpdateComment> updateComment(@PathVariable Integer adId,
                                                               @PathVariable Integer commentId,
                                                               @RequestBody CreateOrUpdateComment createOrUpdateComment,
                                                               Authentication authentication) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, createOrUpdateComment, authentication));
    }
}

