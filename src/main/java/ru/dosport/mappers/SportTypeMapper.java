package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;
import ru.dosport.entities.UserSportType;

import java.util.List;

/**
 * Маппер, преобразующий классы SportType и SportTypeDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface SportTypeMapper {

    @Mappings({
            @Mapping(target = "sportTypeId", source = "entity.id"),
    })
    SportTypeDto mapEntityToDto(SportType entity);

    List<SportTypeDto> mapEntityToDto(List<SportType> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.sportTypeId"),
    })
    SportType mapDtoToEntity(SportTypeDto dto);

    List<SportType> mapDtoToEntity(List<SportTypeDto> dtos);

    SportTypeDto mapEntityToDto(UserSportType entity);

    List<SportTypeDto> mapUserEntityToDto(List<UserSportType> entities);
}
