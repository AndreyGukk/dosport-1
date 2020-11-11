package ru.dosport.services.api;

import org.springframework.http.ResponseEntity;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.entities.SportType;

import java.util.List;

/**
 * Сервис Видов спорта.
 */
public interface SportTypeService {

    /**
     * Выдаёт вид спорта по его id
     *
     * @param id вида спорта
     * @return dto вид спорта
     */
    SportTypeDto getSportTypeDtoById(Short id);

    /**
     * Выдает список всех видов спорта
     *
     * @return список видов спорта
     */
    List<SportTypeDto> getAllSportTypeDto();

    /**
     * Выдаёт вид спорта по его названию
     *
     * @param title название вида спорта
     * @return dto вид спорта
     */
    SportType getSportTypeByTitle(String title);

    /**
     * Добавляет вид спорта
     *
     * @param sportTitle название нового вида спорта на добавление
     * @return вид спорта, сохраненный в репозитории
     */
    SportTypeDto save(String sportTitle);

    /**
     * Удаляет вид спорта по id
     *
     * @param id запрос на добавление вида спорта
     * @return вид спорта, сохраненный в репозитории
     */
    Boolean deleteById(Short id);

    /**
     * Обновляет данные вида спорта
     * @param id индентификатор вида спорта
     * @param tittle название вида спорта
     * @return dto вида спорта
     */
    SportTypeDto update(Short id, String tittle);
}
