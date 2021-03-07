package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.dto.UserSportGroundDto;
import ru.dosport.entities.SportGround;
import ru.dosport.entities.UserSportGround;

import java.util.List;

/**
 * Маппер, преобразующий классы SportGround и SportGroundDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {SportTypeMapper.class, EventMapper.class, SurfaceTypeMapper.class,
                InfrastructureMapper.class, MetroStationMapper.class})
public interface SportGroundMapper {

    SportGroundDto mapEntityToDto(SportGround entity);

    List<SportGroundDto> mapEntityToDto(List<SportGround> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.sportGroundId"),
    })
    SportGround mapDtoToEntity(SportGroundDto dto);

    SportGround mapRequestToEntity(SportGroundRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "events", ignore = true)
    })
    SportGround update(@MappingTarget SportGround entity, SportGroundRequest request);

    UserSportGround mapUserSportGroundDtoToEntity(UserSportGroundDto dto);

    UserSportGroundDto mapUserSportGroundEntityToDto(UserSportGround entity);

    List<UserSportGround> mapUserSportGroundDtoToEntity(List<UserSportGroundDto> dto);

    List<UserSportGroundDto> mapUserSportGroundEntityToDto(List<UserSportGround> entities);
}
