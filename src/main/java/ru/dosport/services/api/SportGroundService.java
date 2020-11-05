package ru.dosport.services.api;

import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.entities.SportGround;

import java.util.List;

/**
 * Сервис Спортивных площадок.
 */
public interface SportGroundService {

    /**
     * Возвращает площадку по идентификатору
     * @param id идентификатор площадки
     * @return dto площадки
     */
    SportGroundDto getDtoById(Long id);

    /**
     * Возвращает все площадки
     * @return список dto площадок
     */
    List<SportGroundDto> getAllDto();

    /**
     * Возвращает все площадки города
     * @param city город
     * @return список dto площадок
     */
    List<SportGroundDto> getAllDto(String city);

    /**
     * Возвращает площадку по идентификатору
     * @param id идентификатор площадки
     * @return сущность площадки
     */
    SportGround getById(Long id);

    /**
     * Создаёт площадку
     * @param request запрос, с адресом, названием, широтой и долготой, видом спорта
     * @return dto новой площадки
     */
    SportGroundDto create(SportGroundRequest request);
}
