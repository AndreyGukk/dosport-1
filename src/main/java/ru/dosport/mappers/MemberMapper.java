package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.EventMember;
import ru.dosport.entities.User;
import ru.dosport.security.JwtUser;

import java.util.List;

/**
 * Маппер, преобразующий классы User и UserDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, GenderMapper.class})
public interface MemberMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.user.id"),
            @Mapping(target = "username", source = "entity.user.username"),
            @Mapping(target = "firstName", source = "entity.user.firstName"),
            @Mapping(target = "lastName", source = "entity.user.lastName"),
            @Mapping(target = "gender", source = "entity.user.gender"),
            @Mapping(target = "info", source = "entity.user.info"),
            @Mapping(target = "photoLink", source = "entity.user.photoLink"),
            @Mapping(target = "hideBirthdayDate", ignore = true),
            @Mapping(target="birthdayDate", source = "entity.user.birthdayDate", dateFormat = "dd-MM-yyyy")
    })
    UserDto mapEntityToDto(EventMember entity);

    List<UserDto> mapEntityToDto(List<EventMember> entities);
}
