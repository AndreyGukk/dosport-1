package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.entities.UserSportType;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSportTypeMapper {

    UserSportType mapDtoToEntity(UserSportTypeDto dto);

    UserSportTypeDto mapEntityToDto(UserSportType entity);

    List<UserSportType> mapDtoToEntity(List<UserSportTypeDto> dto);

    List<UserSportTypeDto> mapEntityToDto(List<UserSportType> entities);
}
