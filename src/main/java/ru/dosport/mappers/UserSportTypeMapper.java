package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.Authentication;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.entities.UserSportType;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserSportTypeMapper {
    UserSportType mapDtoToEntity (UserSportTypeDto dto);
    UserSportTypeDto mapEntityToDto(UserSportType entity);

    List<UserSportType> mapDtoToEntity (List<UserSportTypeDto> dtos);
    List<UserSportTypeDto> mapEntityToDto (List<UserSportType> entities);

    //todo тут надо разобраться
    @Mapping({
            @Mapping(target="userId", source = "userId"),
            @Mapping(target="userId", source = "userId"),
            @Mapping(target="userId", source = "userId")
    })
    List<UserSportType> mapSportTypeToEmptyUserSportType (List<SportType> sportTypes, Long userId);
}
