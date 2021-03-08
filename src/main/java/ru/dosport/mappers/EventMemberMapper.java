package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.MemberDto;
import ru.dosport.entities.EventMember;

import java.util.List;

/**
 * Маппер, преобразующий классы EventMember и MemberDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ParticipationStatusMapper.class})
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
