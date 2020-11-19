package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.dto.UserSportGroundDto;
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
     * Возращает площадки из списка индетификаторов
     * @param idList список идентификаторов площадок
     * @return список dto площадок
     */
    List<SportGroundDto> getAllDtoById(List<Long> idList);

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

    /**
     * Обновляет данные о площадке
     * @param id индентификатор площадки
     * @param sportGroundDto dto с новыми данными
     * @param authentication
     * @return dto с новыми данными
     */
    SportGroundDto update(Long id, SportGroundDto sportGroundDto, Authentication authentication);

    /**
     * Удаление площадки
     * @param id индентификатор площадки
     * @param authentication
     * @return существует ли площадка
     */
    boolean delete(Long id, Authentication authentication);

    /**
     * Получение списка избранных площадок пользователя
     * @param authentication
     * @return список
     */
    List<SportGroundDto> getAllDtoByAuth(Authentication authentication);

    /**
     * Добавление площадки в избранное
     * @param sportGroundDto площадка
     * @param authentication
     * @return площадка
     */
    UserSportGroundDto addUserSportGroundDtoByAuth(Authentication authentication, SportGroundDto sportGroundDto);

    /**
     * Добавление площадки в избранное
     * @param id площадка
     * @param authentication
     * @return существует ли площадка
     */
    boolean deleteBySportGroundId(Long id, Authentication authentication);

    /**
     * Проверяет существование площадки
     * @param sportGroundId индентификатор площадки
     * @return true, false
     */
    boolean exists(Long sportGroundId);
}
