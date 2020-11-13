package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.CommentSportGroundDto;
import ru.dosport.dto.CommentSportGroundRequest;
import ru.dosport.entities.CommentSportGround;

import java.util.List;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface CommentSportGroundMapper {

    @Mappings({
            @Mapping(target = "commentId", source = "entity.id"),
            @Mapping(target = "userId", source = "entity.userId"),
            @Mapping(target = "sportGroundId", source = "entity.sportGround.id"),
            @Mapping(target = "userFullName", source = "entity.userFullName"),
            @Mapping(target = "date", source = "entity.date", dateFormat = "dd-MM-yyyy")
    })
    CommentSportGroundDto mapEntityToDto(CommentSportGround entity);

    List<CommentSportGroundDto> mapEntityToDto(List<CommentSportGround> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.commentId"),
            @Mapping(target = "userId", source = "dto.userId"),
            @Mapping(target = "sportGround", ignore = true),
            @Mapping(target = "userFullName", source = "dto.userFullName"),
            @Mapping(target = "date", source = "dto.date", dateFormat = "dd-MM-yyyy")
    })
    CommentSportGround mapDtoToEntity(CommentSportGroundDto dto);

    @Mappings({
            @Mapping(target = "sportGround", ignore = true)
    })
    CommentSportGround mapRequestToEntity(CommentSportGroundRequest dto);
}
