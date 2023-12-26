package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentService {
    Comments getCommentsByAdId(Integer adId);

    CommentDto addComment(Integer adId, CreateOrUpdateComment createOrUpdateComment, Authentication authentication);

    void deleteComment(int adId, int commentId, Authentication authentication);
    void deleteAllCommentByPk(int pk);

    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment, Authentication authentication);
}