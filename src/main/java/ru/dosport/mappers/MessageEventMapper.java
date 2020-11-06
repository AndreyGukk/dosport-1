package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.MessageEventDto;
import ru.dosport.entities.MessageEvent;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageEventMapper {

    @Mapping(target = "eventId", source = "entity.event.id")
    MessageEventDto mapEntityToDto(MessageEvent entity);

    List<MessageEventDto> mapEntityToDto(List<MessageEvent> entities);
}
