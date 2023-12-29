package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateOrUpdateCommentMapper {
    CreateOrUpdateCommentMapper INSTANCE = Mappers.getMapper(CreateOrUpdateCommentMapper.class);

    @Mapping(target = "text", source = "text")
    CreateOrUpdateComment toDto(Comment comment);

    @Mapping(target = "text", source = "text")
    Comment toModel(CreateOrUpdateComment createOrUpdateComment);
}
