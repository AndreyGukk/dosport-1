package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.EventMessageDto;
import ru.dosport.entities.EventMessage;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMessageMapper {

    @Mapping(target = "eventId", source = "entity.event.id")
    EventMessageDto mapEntityToDto(EventMessage entity);

    List<EventMessageDto> mapEntityToDto(List<EventMessage> entities);
}
