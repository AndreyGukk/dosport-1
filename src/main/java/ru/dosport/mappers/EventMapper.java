package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.entities.Event;

import java.util.List;
import java.util.Set;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {SportTypeMapper.class, UserMapper.class})
public interface EventMapper {

    @Mappings({
            @Mapping(target = "eventId", source = "entity.id"),
            @Mapping(target = "sportGroundId", source = "entity.sportGround.id")
    })
    EventDto mapEntityToDto(Event entity);

    List<EventDto> mapEntityToDto(List<Event> entities);

    List<EventDto> mapEntityToDto(Set<Event> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.eventId")
    })
    Event mapDtoToEntity(EventDto dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creationDateTime", ignore = true),
            @Mapping(target = "sportGround", ignore = true),
            @Mapping(target = "organizer", ignore = true),
            @Mapping(target = "usersAmount", ignore = true),
            @Mapping(target = "messagesAmount", ignore = true),
            @Mapping(target = "participants", ignore = true),
            @Mapping(target = "invitations", ignore = true),
            @Mapping(target = "startDateTime", source = "dto.startDateTime", dateFormat = "dd-MM-yyyy HH:mm"),
            @Mapping(target = "endDateTime", source = "dto.endDateTime", dateFormat = "dd-MM-yyyy HH:mm")
    })
    Event update(@MappingTarget Event entity, EventRequest dto);
}
