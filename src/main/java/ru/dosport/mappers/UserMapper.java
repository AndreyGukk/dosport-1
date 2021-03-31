package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.security.JwtUser;
import ru.dosport.entities.User;

import java.util.List;
import java.util.Set;

import static ru.dosport.helpers.Patterns.LOCAL_DATE_PATTERN;

/**
 * Маппер, преобразующий классы User и UserDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GenderMapper.class})
public interface UserMapper {

    @Mappings({
            @Mapping(target="birthdayDate", source = "entity.birthdayDate", dateFormat = LOCAL_DATE_PATTERN)
    })
    UserDto mapEntityToDto(User entity);

    List<UserDto> mapEntityToDto(List<User> entities);

    List<UserDto> mapEntityToDto(Set<User> entities);

    Set<UserDto> mapSetEntityToDto(Set<User> entities);

    JwtUser mapEntityToJwt(User entity);

    @Mappings({
            @Mapping(target="enabled", constant = "false")
    })
    User mapDtoToEntity(UserRequest dto);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="password", ignore = true),
            @Mapping(target="enabled", ignore = true),
            @Mapping(target="photoLink", ignore = true),
            @Mapping(target="authorities", ignore = true),
            @Mapping(target="subscriptions", ignore = true),
            @Mapping(target="subscribers", ignore = true),
            @Mapping(target="sportGrounds", ignore = true),
            @Mapping(target="sports", ignore = true),
            @Mapping(target="events", ignore = true),
            @Mapping(target="eventInvitations", ignore = true),
            @Mapping(target="birthdayDate", source="dto.birthdayDate", dateFormat = LOCAL_DATE_PATTERN)
    })
    User update(@MappingTarget User entity, UserDto dto);
}
