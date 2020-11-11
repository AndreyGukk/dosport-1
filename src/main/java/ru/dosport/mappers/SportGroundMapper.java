package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.entities.SportGround;

import java.util.List;

/**
 * Маппер, преобразующий классы SportGround и SportGroundDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {SportTypeMapper.class, EventMapper.class, CommentSportGroundMapper.class})
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
}
