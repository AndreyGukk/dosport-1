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
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMemberMapper {

    @Mappings({
            @Mapping(target = "eventId", source = "entity.eventId"),
            @Mapping(target = "userId", source = "entity.userId"),
            @Mapping(target = "userStatus", source = "entity.status")
    })
    MemberDto mapEntityToDto(EventMember entity);

    List<MemberDto> mapEntityToDto(List<EventMember> entries);

    @Mappings({
            @Mapping(target = "eventId", source = "dto.eventId"),
            @Mapping(target = "userId", source = "dto.userId"),
            @Mapping(target = "status", source = "dto.userStatus")
    })
    EventMember mapDtoToEntity(MemberDto dto);
}
