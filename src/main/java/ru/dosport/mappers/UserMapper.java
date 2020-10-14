package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.User;
import ru.dosport.entities.JwtUser;

import java.util.List;

/**
 * Маппер, преобразующий классы User и UserDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AuthorityMapper.class})
public interface UserMapper {

    @Mappings({
            @Mapping(target="creationDate", source = "entity.creationDate", dateFormat = "dd-MM-yyyy")
    })
    UserDto userToUserDto(User entity);

    List<UserDto> userToUserDto(List<User> entities);

    JwtUser userToJwtUser(User entity);

    @Mappings({
            @Mapping(target="creationDate", source="dto.creationDate", dateFormat = "dd-MM-yyyy")
    })
    User userDtoToUser(UserDto dto);

    User userRequestToUser(UserRequest dto);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="username", ignore = true),
            @Mapping(target="password", ignore = true),
            @Mapping(target="authorities", ignore = true),
            @Mapping(target="creationDate", ignore = true),
    })
    User updateUser(@MappingTarget User entity, UserDto dto);
}
