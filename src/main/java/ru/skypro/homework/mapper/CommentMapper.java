package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    @Mapping(source = "comment.createdAt", target = "createdAt")
    @Mapping(source = "comment.pk", target = "pk")
    @Mapping(source = "comment.text", target = "text")
    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.userImage", target = "authorImage")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    CommentDto toDto(Comment comment, User user);
    @Mapping(target = "user.id", source = "author")
    @Mapping(target = "user.userImage",  source= "authorImage")
    @Mapping(target = "user.firstName", source = "authorFirstName")
    Comment toModel(CommentDto commentDto);

    Comment toModel(CreateOrUpdateComment createOrUpdateComment);
}
