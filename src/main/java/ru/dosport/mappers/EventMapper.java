package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.entities.Event;

import java.util.List;
import java.util.Set;

import static ru.dosport.helpers.Patterns.LOCAL_DATE_TIME_PATTERN;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {SportTypeMapper.class, UserMapper.class})
public interface EventMapper {

    @Mappings({
            @Mapping(target = "eventId", source = "entity.id"),
            @Mapping(target = "sportGroundId", source = "entity.sportGround"),
            @Mapping(target = "creationDateTime", source = "entity.creationDateTime", dateFormat = LOCAL_DATE_TIME_PATTERN),
            @Mapping(target = "startDateTime", source = "entity.startDateTime", dateFormat = LOCAL_DATE_TIME_PATTERN),
            @Mapping(target = "endDateTime", source = "entity.endDateTime", dateFormat = LOCAL_DATE_TIME_PATTERN)
    })
    EventDto mapEntityToDto(Event entity);

    List<EventDto> mapEntityToDto(List<Event> entities);

    List<EventDto> mapEntityToDto(Set<Event> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creationDateTime", ignore = true),
            @Mapping(target = "sportGround", ignore = true),
            @Mapping(target = "organizer", ignore = true),
            @Mapping(target = "usersAmount", ignore = true),
            @Mapping(target = "messagesAmount", ignore = true),
            @Mapping(target = "participants", ignore = true),
            @Mapping(target = "invitations", ignore = true),
            @Mapping(target = "startDateTime", source = "dto.startDateTime", dateFormat = LOCAL_DATE_TIME_PATTERN),
            @Mapping(target = "endDateTime", source = "dto.endDateTime", dateFormat = LOCAL_DATE_TIME_PATTERN)
    })
    Event update(@MappingTarget Event entity, EventRequest dto);
}
