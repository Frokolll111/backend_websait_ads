package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.CreateOrUpdateCommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Получение всех комментариев в объявлении о его id
     * @param adId id объявления
     * @return Comments
     */
    @Override
    public Comments getCommentsByAdId(Integer adId) {
        List<Comment> comments = commentRepository.findAllByAd_Pk(adId);
        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> CommentMapper.INSTANCE.toDto(comment, comment.getUser()))
                .collect(Collectors.toList());

        return new Comments(commentDtos);
    }

    /**
     * Добавление комментария к объявлению по его id
     * @param adId id объявления
     * @param createOrUpdateComment текст комментария
     * @return CommentDto
     */
    @Override
    public CommentDto addComment(Integer adId, CreateOrUpdateComment createOrUpdateComment) {
        Comment comment = CreateOrUpdateCommentMapper.INSTANCE.toModel(createOrUpdateComment);
        Ad ad = new Ad();
        ad.setPk(adId);
        comment.setAd(ad);
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

        Comment savedComment = commentRepository.save(updatedComment);
        return CommentMapper.INSTANCE.toDto(savedComment, savedComment.getUser());
    }
}