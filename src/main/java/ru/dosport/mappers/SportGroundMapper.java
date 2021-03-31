package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.entities.SportGround;

import java.util.List;
import java.util.Set;

import static ru.dosport.helpers.Patterns.LOCAL_TIME_PATTERN;

/**
 * Маппер, преобразующий классы SportGround и SportGroundDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {SportTypeMapper.class, EventMapper.class, SurfaceTypeMapper.class,
                InfrastructureMapper.class, MetroStationMapper.class})
public interface SportGroundMapper {

    @Mappings({
            @Mapping(target = "sportGroundId", source = "entity.id"),
            @Mapping(target = "openingTime", source = "entity.openingTime", dateFormat = LOCAL_TIME_PATTERN),
            @Mapping(target = "closingTime", source = "entity.closingTime", dateFormat = LOCAL_TIME_PATTERN)
    })
    SportGroundDto mapEntityToDto(SportGround entity);

    @Mappings({
            @Mapping(target = "sportTypes", ignore = true),
            @Mapping(target = "openingTime", source = "dto.openingTime", dateFormat = LOCAL_TIME_PATTERN),
            @Mapping(target = "closingTime", source = "dto.closingTime", dateFormat = LOCAL_TIME_PATTERN)
    })
    SportGround mapDtoToEntity(SportGroundRequest dto);

    @Mappings({
            @Mapping(target = "sportTypes", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "events", ignore = true)
    })
    SportGround update(@MappingTarget SportGround entity, SportGroundRequest request);

    List<SportGroundDto> mapEntityToDto(List<SportGround> entities);

    Set<SportGroundDto> mapEntityToDto(Set<SportGround> entities);
}
