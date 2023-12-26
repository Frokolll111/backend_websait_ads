package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.NoRightsException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.CreateOrUpdateCommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, AdRepository adRepository, UserRepository userRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Получение всех комментариев в объявлении о его id
     *
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
     *
     * @param adId                  id объявления
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
     *
     * @param adId      id объявления
     * @param commentId id комментария
     */
    @Override
    public void deleteComment(int adId, int commentId, Authentication authentication) {
        Comment comment = commentRepository.findByAd_PkAndPk(adId, commentId).orElseThrow();
        String currentAuthor = comment.getUser().getUsername();
        if (userService.checkUserRole(currentAuthor, authentication)) {
            commentRepository.delete(comment);
        }
    }
    @Override
    public CreateOrUpdateComment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment, Authentication authentication) {
        Comment comment = commentRepository.findByAd_PkAndPk(adId, commentId).orElseThrow();
        String currentAuthor = comment.getUser().getUsername();
        if (userService.checkUserRole(currentAuthor, authentication)) {
            Comment newComment = commentRepository.getReferenceById(comment.getPk());
            newComment.setText(createOrUpdateComment.getText());
            commentRepository.save(newComment);
        } else {
            throw new NoRightsException("нет прав для редактирования");
        }
        return CreateOrUpdateCommentMapper.INSTANCE.toDto(comment);
    }

    @Override
    public void deleteAllCommentByPk(int pk) {
        commentRepository.deleteAll(commentRepository.findAllByAdPk(pk));
    }
}