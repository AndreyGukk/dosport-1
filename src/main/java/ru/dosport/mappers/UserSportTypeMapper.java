package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.UserSportType;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserSportTypeMapper {
    UserSportType mapDtoToEntity (UserSportTypeDto dto);
    UserSportTypeDto mapEntityToDto(UserSportType entity);

    List<UserSportType> mapDtoToEntity (List<UserSportTypeDto> dtos);
    List<UserSportType> mapEntityToDto (List<UserSportType> entities);

    Map<UserSportType, Byte> mapDtoToEntity (Map<UserSportTypeDto, Byte> dtos);
    Map<UserSportType, Byte> mapEntityToDto (Map<UserSportType, Byte> entities);
}
