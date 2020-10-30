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
public interface EventMemberMapper {

    @Mappings({
            @Mapping(target = "eventId", source = "entity.event.id"),
            @Mapping(target = "user", source = "userDto")
    })
    MemberDto mapEntityToDto(EventMember entity, UserDto userDto);

    @Mappings({
            @Mapping(target = "event", ignore = true),
            @Mapping(target = "userId", source = "dto.user.id")
    })
    EventMember mapDtoToEntity(MemberDto dto);
}
