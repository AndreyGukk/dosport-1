package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.Event;
import ru.dosport.entities.SportType;

import java.util.List;

/**
 * Маппер, преобразующий классы Event и EventDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface SportTypeMapper {

    @Mappings({
            @Mapping(target = "idSportType", source = "entity.id"),
    })
    SportTypeDto mapEntityToDto(SportType entity);

    List<SportTypeDto> mapEntityToDto(List<SportType> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.idSportType"),
    })
    SportType mapDtoToEntity(SportTypeDto dto);

    List<SportType> mapDtoToEntity(List<SportTypeDto> dtos);
}
