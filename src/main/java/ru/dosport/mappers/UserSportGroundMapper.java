package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.UserSportGroundDto;
import ru.dosport.entities.UserSportGround;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSportGroundMapper {
    UserSportGround mapDtoToEntity(UserSportGroundDto dto);

    UserSportGroundDto mapEntityToDto(UserSportGround entity);

    List<UserSportGround> mapDtoToEntity(List<UserSportGroundDto> dto);

    List<UserSportGroundDto> mapEntityToDto(List<UserSportGround> entities);
}
