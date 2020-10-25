package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.entities.Event;

import java.util.List;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {SportTypeMapper.class, MemberMapper.class})
public interface EventMapper {

    @Mappings({
            @Mapping(target = "idEvent", source = "entity.id"),
            @Mapping(target = "dateEvent", source = "entity.date", dateFormat = "dd-MM-yyyy"),
            @Mapping(target="startTimeEvent", source = "entity.startTime", dateFormat = "hh:mm"),
            @Mapping(target="endTimeEvent", source = "entity.endTime", dateFormat = "hh:mm"),
            @Mapping(target="idSportGround", source = "entity.sportGround.id"),
            @Mapping(target="users", source = "entity.members"),
            @Mapping(target="idOrganizer", source = "entity.organizer.id")
    })
    EventDto mapEntityToDto(Event entity);

    List<EventDto> mapEntityToDto(List<Event> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.idEvent"),
            @Mapping(target = "date", source = "dto.dateEvent", dateFormat = "dd-MM-yyyy"),
            @Mapping(target="startTime", source = "dto.startTimeEvent", dateFormat = "hh:mm"),
            @Mapping(target="endTime", source = "dto.endTimeEvent", dateFormat = "hh:mm")
    })
    Event mapDtoToEntity(EventDto dto);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="sportGround", ignore = true),
            @Mapping(target="organizer", ignore = true),
            @Mapping(target="idChat", ignore = true),
            @Mapping(target="members", ignore = true),

            @Mapping(target = "date", source = "dto.dateEvent", dateFormat = "dd-MM-yyyy"),
            @Mapping(target="startTime", source = "dto.startTimeEvent", dateFormat = "hh:mm"),
            @Mapping(target="endTime", source = "dto.endTimeEvent", dateFormat = "hh:mm")
    })
    Event update(@MappingTarget Event entity, EventDto dto);

}
