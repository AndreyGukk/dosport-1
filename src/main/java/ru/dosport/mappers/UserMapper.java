package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.JwtUser;
import ru.dosport.entities.User;

import java.util.List;

/**
 * Маппер, преобразующий классы User и UserDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AuthorityMapper.class, GenderMapper.class})
public interface UserMapper {

    @Mappings({
            @Mapping(target="birthdayDate", source = "entity.birthdayDate", dateFormat = "dd-MM-yyyy")
    })
    UserDto userToUserDto(User entity);

    List<UserDto> userToUserDto(List<User> entities);

    JwtUser userToJwtUser(User entity);

    @Mappings({
            @Mapping(target="birthdayDate", source="dto.birthdayDate", dateFormat = "dd-MM-yyyy")
    })
    User userDtoToUser(UserDto dto);

    User userRequestToUser(UserRequest dto);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="username", ignore = true),
            @Mapping(target="password", ignore = true),
            @Mapping(target="authorities", ignore = true),
    })
    User updateUser(@MappingTarget User entity, UserDto dto);
}
