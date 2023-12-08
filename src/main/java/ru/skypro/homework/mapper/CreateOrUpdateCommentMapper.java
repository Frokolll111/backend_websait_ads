package ru.skypro.homework.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;

public interface CreateOrUpdateCommentMapper {

    @Mapping(source = "text", target = "text")
    Comment createOrUpdateCommentToComment(CreateOrUpdateComment createOrUpdateComment);

    @Mapping(source = "text", target = "text")
    CreateOrUpdateComment commentToCreateOrUpdateComment(Comment comment);
}
