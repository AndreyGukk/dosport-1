package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.dosport.dto.UserDto;
import ru.dosport.entities.User;
import ru.dosport.entities.JwtUser;

import java.util.List;

/**
 * Маппер, преобразующий классы User и UserDto друг в друга
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class})
public interface UserMapper {

    @Mappings({
            @Mapping(target="id", source = "entity.id"),
            @Mapping(target="username", source = "entity.username"),
            @Mapping(target="creationDate", source = "entity.creationDate", dateFormat = "dd-MM-yyyy"),
            @Mapping(target="firstName", source = "entity.firstName"),
            @Mapping(target="lastName", source = "entity.lastName"),
            @Mapping(target="email", source = "entity.email"),
            @Mapping(target="photoLink", source = "entity.photoLink")
    })
    UserDto userToUserDto(User entity);

    List<UserDto> userToUserDto(List<User> entities);

    JwtUser userToJwtUser(User entity);

    @Mappings({
            @Mapping(target="id", source="dto.id"),
            @Mapping(target="username", source="dto.username"),
            @Mapping(target="firstName", source="dto.firstName"),
            @Mapping(target="lastName", source="dto.lastName"),
            @Mapping(target="email", source="dto.email"),
            @Mapping(target="photoLink", source="dto.photoLink")
    })
    User userDtoToUser(UserDto dto);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="username", ignore = true),
            @Mapping(target="password", ignore = true),
            @Mapping(target="authorities", ignore = true),
            @Mapping(target="creationDate", ignore = true),
    })
    User updateUser(@MappingTarget User entity, UserDto dto);
}
