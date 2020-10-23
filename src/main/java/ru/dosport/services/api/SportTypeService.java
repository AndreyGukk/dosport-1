package ru.dosport.services.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.SportTypeDto;

import java.util.List;

/**
 * Интерфейс сервиса видов спорта
 */

@Service
public interface SportTypeService {
    /**
     * Выдает список всех видов спорта
     *
     * @return список видов спорта
     */
    List<SportTypeDto> getAllDto();


    /**
     * Добавляет вид спорта
     *
     * @param sportTypeDto запрос на добавление вида спорта
     * @return вид спорта, сохраненный в репозитории
     */
    SportTypeDto save(SportTypeDto sportTypeDto);

    /**
     * Удаляет вид спорта по id
     *
     * @param id запрос на добавление вида спорта
     * @return вид спорта, сохраненный в репозитории
     */
    Boolean deleteById(Long id);
}
