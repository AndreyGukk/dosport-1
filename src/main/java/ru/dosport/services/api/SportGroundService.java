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
     * Возращает площадки из списка индетификаторов
     * @param idList список идентификаторов площадок
     * @return список dto площадок
     */
    List<SportGroundDto> getAllDtoByIdList(List<Long> idList);


    /**
     * Возвращает все площадки города с заданным видом спорта
     * 
     * @param city город
     * @param sportTypeId идентификатор вида спорта
     * @return список dto площадок
     */
    List<SportGroundDto> getAllDtoByCityAndSportTypeId(String city, Short sportTypeId);

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
     * @param request площадка с новыми данными
     * @param authentication данные авторизации
     * @return dto с новыми данными
     */
    SportGroundDto update(Long id, SportGroundRequest request, Authentication authentication);

    /**
     * Удаление площадки
     * @param id индентификатор площадки
     * @param authentication данные авторизации
     * @return существует ли площадка
     */
    boolean delete(Long id, Authentication authentication);

    /**
     * Получение списка избранных площадок пользователя
     * @param authentication данные авторизации
     * @return список
     */
    List<SportGroundDto> getAllDtoByAuth(Authentication authentication);

    /**
     * Добавление площадки в избранное
     * @param sportGroundDto площадка
     * @param authentication данные авторизации
     * @return площадка
     */
    UserSportGroundDto saveUserSportGroundDtoByAuth(Authentication authentication, SportGroundDto sportGroundDto);

    /**
     * Удаление площадки из избранного
     * @param id площадка
     * @param authentication данные авторизации
     * @return существует ли площадка
     */
    boolean deleteFavoritesBySportGroundId(Long id, Authentication authentication);

    /**
     * Проверяет существование площадки
     * @param sportGroundId индентификатор площадки
     * @return true, false
     */
    boolean exists(Long sportGroundId);
}
