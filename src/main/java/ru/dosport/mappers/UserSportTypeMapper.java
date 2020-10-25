package ru.dosport.mappers;

import org.mapstruct.Mapper;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.UserSportType;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserSportTypeMapper {
    UserSportType mapDtoToEntity (UserSportTypeDto dto);
    UserSportTypeDto mapEntityToDto(UserSportType entity);

    List<UserSportType> mapDtoToEntity (List<UserSportTypeDto> dtos);
    List<UserSportTypeDto> mapEntityToDto (List<UserSportType> entities);
}
