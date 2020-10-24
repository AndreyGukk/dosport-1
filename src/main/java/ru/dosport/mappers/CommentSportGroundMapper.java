package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.CommentSportGroundDto;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.CommentSportGround;
import ru.dosport.entities.Event;
import ru.dosport.entities.SportType;
import ru.dosport.entities.User;

import java.util.List;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface CommentSportGroundMapper {

    @Mappings({
            @Mapping(target = "idComment", source = "entity.id"),
            @Mapping(target = "idUser", source = "entity.user.id"),
            @Mapping(target = "idSportGround", source = "entity.sportGround.id"),
            @Mapping(target = "userName", source = "entity.user", qualifiedByName = "formName"),
            @Mapping(target = "date", source = "entity.date", dateFormat = "dd-MM-yyyy")
    })
    CommentSportGroundDto mapEntityToDto(CommentSportGround entity);

    List<CommentSportGroundDto> mapEntityToDto(List<CommentSportGround> entities);

    CommentSportGround mapDtoToEntity(CommentSportGroundDto dto);

    @Named("formName")
    default String formatNameUser(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

}
