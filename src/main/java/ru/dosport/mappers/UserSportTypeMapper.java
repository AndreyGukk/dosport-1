package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.Authentication;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.entities.UserSportType;

import java.util.List;
import java.util.Map;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSportTypeMapper {
    UserSportType mapDtoToEntity (UserSportTypeDto dto);
    UserSportTypeDto mapEntityToDto(UserSportType entity);

    List<UserSportType> mapDtoToEntity (List<UserSportTypeDto> dtos);
    List<UserSportTypeDto> mapEntityToDto (List<UserSportType> entities);
}
