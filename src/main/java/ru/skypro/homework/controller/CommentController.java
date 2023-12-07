package ru.skypro.homework.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@RestController
@RequestMapping("/ads")
public class CommentController {

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComment(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateComment createOrUpdateComment){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<AdDto> deleteComment(@PathVariable Integer adId,
                                               @PathVariable Integer commentId){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<AdDto> updateComment(@PathVariable Integer adId,
                                               @PathVariable Integer commentId,
                                               @RequestBody CreateOrUpdateComment createOrUpdateComment){
        return ResponseEntity.ok().build();
    }






}
