package ru.dosport.services.api;

import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;

import java.util.List;

public interface SportTypeService {

    SportTypeDto getSportTypeDtoById(Short id);

    List<SportTypeDto> getAllSportTypeDto();

    SportType getSportTypeByTitle(String title);

}
