package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.util.List;

/*
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDto convertToDTO(Comment comment) {
        return commentMapper.commentToCommentDto(comment);
    }

    public Comment convertToEntity(CommentDto commentDto) {
        return commentMapper.commentDtoToComment(commentDto);
    }

    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return commentMapper.commentsToCommentDtos(comments);
    }

    public CommentDto getCommentById(Integer commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::commentToCommentDto)
                .orElse(null);
    }

    public void addComment(CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        commentRepository.save(comment);
    }

    public void updateComment(Integer commentId, CommentDto commentDto) {
        if (commentRepository.existsById(commentId)) {
            Comment updatedComment = convertToEntity(commentDto);
            updatedComment.setPk(commentId);
            commentRepository.save(updatedComment);
        }
    }

    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

}
*/
