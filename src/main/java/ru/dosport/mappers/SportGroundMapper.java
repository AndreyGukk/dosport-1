package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.entities.SportGround;

import java.util.List;

/**
 * Маппер, преобразующий классы SportGround и SportGroundDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {SportTypeMapper.class, EventMapper.class})
public interface SportGroundMapper {

    @Mappings({
            @Mapping(target = "sportGroundId", source = "entity.id"),
            @Mapping(target = "sportTypes", source = "entity.sportType"),
            @Mapping(target = "latitude", source = "entity.latitude"),
            @Mapping(target = "longitude", source = "entity.longitude")
    })
    SportGroundDto mapEntityToDto(SportGround entity);

    List<SportGroundDto> mapEntityToDto(List<SportGround> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.sportGroundId"),
    })
    SportGround mapDtoToEntity(SportGroundDto dto);

    @Mappings({
            @Mapping(target = "address", source = "request.address"),
            @Mapping(target = "sportType", source = "request.sportTypes"),
            @Mapping(target = "title", source = "request.title"),
            @Mapping(target = "city", source = "request.city"),
            @Mapping(target = "latitude", source = "request.latitude"),
            @Mapping(target = "longitude", source = "request.longitude")
    })
    SportGround mapRequestToEntity(SportGroundRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "events", ignore = true),
    })
    SportGround update(@MappingTarget SportGround entity, SportGroundDto dto);
}
