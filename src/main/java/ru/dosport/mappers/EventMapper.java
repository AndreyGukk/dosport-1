package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserEventDto;
import ru.dosport.entities.Event;
import ru.dosport.entities.UserEvent;
import ru.dosport.services.api.SportTypeService;

import java.util.List;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {SportTypeMapper.class, EventMemberMapper.class})
public interface EventMapper {

    @Mappings({
            @Mapping(target = "eventId", source = "entity.id"),
            @Mapping(target = "dateEvent", source = "entity.date", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "startTimeEvent", source = "entity.startTime", dateFormat = "hh:mm"),
            @Mapping(target = "endTimeEvent", source = "entity.endTime", dateFormat = "hh:mm"),
            @Mapping(target = "sportGroundId", source = "entity.sportGround.id"),
            @Mapping(target = "members", source = "entity.members"),
            @Mapping(target = "organizerId", source = "entity.organizerId")
    })
    EventDto mapEntityToDto(Event entity);

    List<EventDto> mapEntityToDto(List<Event> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.eventId"),
            @Mapping(target = "date", source = "dto.dateEvent", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "startTime", source = "dto.startTimeEvent", dateFormat = "hh:mm"),
            @Mapping(target = "endTime", source = "dto.endTimeEvent", dateFormat = "hh:mm")
    })
    Event mapDtoToEntity(EventDto dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "sportGround", ignore = true),
            @Mapping(target = "organizerId", ignore = true),
            @Mapping(target = "chatId", ignore = true),
            @Mapping(target = "members", ignore = true),
            @Mapping(target = "date", source = "dto.dateEvent", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "startTime", source = "dto.startTimeEvent", dateFormat = "hh:mm"),
            @Mapping(target = "endTime", source = "dto.endTimeEvent", dateFormat = "hh:mm")
    })
    Event update(@MappingTarget Event entity, EventDto dto);

    @Mappings({
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "eventId", source = "entity.eventId", ignore = true),
            @Mapping(target = "startTimeEvent", source = "entity.startTimeEvent", dateFormat = "hh:mm"),
            @Mapping(target = "endTimeEvent", source = "entity.endTimeEvent", dateFormat = "hh:mm"),
            @Mapping(target = "date", source = "entity.dateEvent", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "chatId", source = "entity.chatId", ignore = true),
            @Mapping(target = "sportType", source = "entity.sportType", ignore = true),
            @Mapping(target = "sportGroundId", source = "entity.sportGround", ignore = true)
    })
    UserEventDto mapUserEventToUserEventDto(UserEvent entity);

    List<UserEventDto> mapUserEventToUserEventDto(List<UserEvent> entity);
}
