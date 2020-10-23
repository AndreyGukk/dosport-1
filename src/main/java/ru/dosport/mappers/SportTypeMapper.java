package ru.dosport.mappers;

import org.mapstruct.Mapper;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;

@Mapper
public interface SportTypeMapper {
    SportType mapDtoToEntity (SportTypeDto dto);
    SportTypeDto mapEntityToDto(SportType entity);
}
