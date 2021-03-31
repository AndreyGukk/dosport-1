package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.*;
import ru.dosport.specifications.SportGroundSearchCriteria;

import java.util.List;
import java.util.Set;

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
     * Возвращает список площадок, имеющие определенные критерии поиска
     *
     * @param searchCriteria критерии поиска
     * @param pageNumber номер страницы
     * @return список площадок
     */
    List<SportGroundDto> getAllDtoBySearchCriteria(SportGroundSearchCriteria searchCriteria, Integer pageNumber);


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
    Set<SportGroundDto> getFavoriteSportGroundsByAuth(Authentication authentication);

    /**
     * Добавление площадки в избранное
     * @param id индентификатор площадки
     * @param authentication данные авторизации
     * @return появилась ли площадка
     */
    boolean addFavoriteSportGroundByAuthAndId(Authentication authentication, Long id);

    /**
     * Удаление площадки из избранного
     * @param id индентификатор площадки
     * @param authentication данные авторизации
     * @return существует ли площадка
     */
    boolean deleteFavoriteSportGroundByAuthAndId(Authentication authentication, Long id);

    /**
     * Проверяет существование площадки
     * @param sportGroundId индентификатор площадки
     * @return true, false
     */
    boolean exists(Long sportGroundId);

    /**
     * Возвращает список людей, подписанных на площадку
     * @param id идентификатор площадки
     */
    Set<UserDto> getSubscribersBySportGroundId(Long id);


    /**
     * Возвращает список мероприятий на площадке
     * @param id идентификатор площадки
     * @param page номер страницы
     */
    List<EventDto> getEventsBySportGroundId(Long id, Integer page);

    /**
     * Возвращает список инфраструктуры на площадке
     * @param id идентификатор площадки
     */
    Set<String> getInfrastructuresBySportGroundId(Long id);
}
