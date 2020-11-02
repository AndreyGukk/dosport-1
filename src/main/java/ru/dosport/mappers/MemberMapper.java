package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.MemberDto;
import ru.dosport.entities.EventMember;

import java.util.List;

/**
 * Маппер, преобразующий классы User и UserDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, GenderMapper.class})
public interface MemberMapper {

    @Mappings({
            @Mapping(target = "eventId", source = "entity.event.id")
    })
    MemberDto mapEntityToDto(EventMember entity);

    List<MemberDto> mapEntityToDto(List<EventMember> entities);

    @Mapping(target = "event", ignore = true)
    EventMember mapDtoToEntity(MemberDto dto);
}
