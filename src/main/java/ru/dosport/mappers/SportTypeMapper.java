package ru.dosport.mappers;

import org.mapstruct.Mapper;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;

import java.util.List;

@Mapper
public interface SportTypeMapper {
    SportType mapDtoToEntity (SportTypeDto dto);
    SportTypeDto mapEntityToDto(SportType entity);

    List <SportType> mapDtoToEntity (List<SportTypeDto> dto);
    List<SportTypeDto> mapEntityToDto(List<SportType> entity);
}
