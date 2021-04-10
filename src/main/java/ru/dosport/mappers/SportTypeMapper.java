package ru.dosport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.dosport.entities.SportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Маппер, преобразующий классы SportType и SportTypeDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface SportTypeMapper {

    default String mapEntityToString(SportType sportType){
        return sportType.getTitle();
    }

    Set<String> mapEntityToString(List<SportType>entities);

    Set<String> mapEntityToString(Set<SportType>entities);
}
