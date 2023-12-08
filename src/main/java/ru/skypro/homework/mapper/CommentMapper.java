package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mappings({
            @Mapping(source = "user.id", target = "author"),
            @Mapping(source = "user.userImage", target = "authorImage"),
            @Mapping(source = "user.firstName", target = "authorFirstName"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "pk", target = "pk"),
            @Mapping(source = "text", target = "text")
    })
    CommentDto commentToCommentDto(Comment comment);

    @Mappings({
            @Mapping(source = "author", target = "user.id"),
            @Mapping(source = "authorImage", target = "user.userImage"),
            @Mapping(source = "authorFirstName", target = "user.firstName"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "pk", target = "pk"),
            @Mapping(source = "text", target = "text")
    })
    Comment commentDtoToComment(CommentDto commentDto);

    List<CommentDto> commentsToCommentDtos(List<Comment> comments);

    List<Comment> commentDtosToComments(List<CommentDto> commentDtos);
}