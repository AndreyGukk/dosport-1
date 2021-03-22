package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.MessageDto;
import ru.dosport.entities.Message;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, EventMapper.class})
public interface MessageMapper {

    @Mappings({
            @Mapping(target = "messageId", source = "entity.id"),
            @Mapping(target = "eventId", source = "entity.event.id"),
            @Mapping(target = "userId", source = "entity.user.id"),
            @Mapping(target = "username", source = "entity.user.username"),
            @Mapping(target = "photoLink", source = "entity.user.photoLink"),
            @Mapping(target = "text", source = "entity.text"),
    })
    MessageDto mapEntityToDto(Message entity);

    List<MessageDto> mapEntityToDto(List<Message> entities);
}
