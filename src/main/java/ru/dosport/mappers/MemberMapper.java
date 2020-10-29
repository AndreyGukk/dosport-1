package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.MemberDto;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.EventMember;
import ru.dosport.entities.User;
import ru.dosport.security.JwtUser;

import java.util.List;

/**
 * Маппер, преобразующий классы EventMember и MemberDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface MemberMapper {

    @Mappings({
            @Mapping(target = "eventId", source = "entity.event.id")
    })
    MemberDto mapEntityToDto(EventMember entity);

    List<MemberDto> mapEntityToDto(List<EventMember> entities);

    @Mapping(target = "event", ignore = true)
    EventMember mapDtoToEntity(MemberDto dto);
}
