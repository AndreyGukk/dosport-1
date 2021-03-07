package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.security.JwtUser;
import ru.dosport.entities.User;

import java.util.List;

/**
 * Маппер, преобразующий классы User и UserDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(target="birthdayDate", source = "entity.birthdayDate", dateFormat = "dd-MM-yyyy")
    })
    UserDto mapEntityToDto(User entity);

    List<UserDto> mapEntityToDto(List<User> entities);

    JwtUser mapEntityToJwt(User entity);

    @Mappings({
            @Mapping(target="enabled", constant = "true")
    })
    User mapDtoToEntity(UserRequest dto);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="username", ignore = true),
            @Mapping(target="password", ignore = true),
            @Mapping(target="enabled", ignore = true),
            @Mapping(target="authorities", ignore = true),
            @Mapping(target="friends", ignore = true),
            @Mapping(target="favoriteSportGrounds", ignore = true),
            @Mapping(target="birthdayDate", source="dto.birthdayDate", dateFormat = "dd-MM-yyyy")
    })
    User update(@MappingTarget User entity, UserDto dto);
}
