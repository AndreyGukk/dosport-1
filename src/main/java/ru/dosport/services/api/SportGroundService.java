package ru.dosport.services.api;

import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.entities.SportGround;

import java.util.List;

/**
 * Сервис Спортивных площадок.
 */
public interface SportGroundService {

    SportGroundDto getDtoById(Long id);

    List<SportGroundDto> getAllDto();

    List<SportGroundDto> getAllDto(String city);

    SportGround getById(Long id);

    SportGroundDto create(SportGroundRequest request);
}
