package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.CreateOrUpdateCommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import javax.persistence.EntityNotFoundException;
import java.net.Authenticator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;

    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, AdRepository adRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    /**
     * Получение всех комментариев в объявлении о его id
     * @param adId id объявления
     * @return Comments
     */
    @Override
    public Comments getCommentsByAdId(Integer adId) {
        List<Comment> comments = commentRepository.findAllByAdPk(adId);
        List<CommentDto> commentsDto = comments.stream()
                .map(comment -> CommentMapper.INSTANCE.toDto(comment, comment.getUser()))
                .collect(Collectors.toList());

        return new Comments(commentsDto);
    }

    /**
     * Добавление комментария к объявлению по его id
     * @param adId id объявления
     * @param createOrUpdateComment текст комментария
     * @return CommentDto
     */
    @Override
    public CommentDto addComment(Integer adId, CreateOrUpdateComment createOrUpdateComment, Authentication authentication) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("Объявление не найдено по ID: " + adId));

        Comment comment = CreateOrUpdateCommentMapper.INSTANCE.toModel(createOrUpdateComment);
        comment.setAd(ad);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(userRepository.findByUserName(authentication.getName()));

        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.INSTANCE.toDto(savedComment, savedComment.getUser());
    }

    /**
     * Удаление комментария к объявлению по его id
     * @param adId id объявления
     * @param commentId id комментария
     */
    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * Изменения комментария к объявлению по его id
     * @param adId id объявления
     * @param commentId id комментария
     * @param createOrUpdateComment текст комментария
     * @return CommentDto
     */
    @Override
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Комментарий не найден"));

        Comment updatedComment = CreateOrUpdateCommentMapper.INSTANCE.toModel(createOrUpdateComment);
        updatedComment.setAd(existingComment.getAd());
        updatedComment.setUser(existingComment.getUser());
        updatedComment.setPk(existingComment.getPk());
        updatedComment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(updatedComment);
        return CommentMapper.INSTANCE.toDto(savedComment, savedComment.getUser());
    }
}