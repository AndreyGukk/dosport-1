package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.specifications.EventSearchCriteria;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис Мероприятий
 */
public interface EventService {

    /**
     * Возвращает мероприятие по его идентификатору
     *
     * @param id идентификатор мероприятия
     * @return dto мероприятия
     */
    EventDto getDtoById(Long id);

    /**
     * Возвращает список мероприятий, имеющие определенные критерии поиска
     *
     * @param searchCriteria критерии поиска
     * @param pageNumber номер страницы
     * @return список мероприятий
     */
    List<EventDto> getAllDtoBySearchCriteria(EventSearchCriteria searchCriteria, Integer pageNumber);

    /**
     * Возвращает список мероприятий для определенной площадки по идентификатору площадки
     *
     * @param sportGroundId идентификатор площадки, для которой получаем список мероприятий
     * @param page страница
     * @return список мероприятий
     */
    List<EventDto> getAllDtoBySportGroundId(Long sportGroundId, Integer page);

    /**
     * Возвращает существует ли мероприятие
     *
     * @param eventId идентификатор мероприятия
     * @return существует ли мероприятие
     */
    boolean existById(Long eventId);

    /**
     * Создает новое мероприятие.
     *
     * @param eventRequest   запрос, содержащий данные мероприятия
     * @param authentication данные авторизации
     * @return новое мероприятие, сохраненное в репозитории
     */
    EventDto save(EventRequest eventRequest, Authentication authentication);

    /**
     * Изменяет данные мероприятия по его идентификатору.
     *
     * @param eventRequest   мероприятие с измененными данными
     * @param eventId        индекс мероприятия
     * @param authentication данные авторизации
     */
    EventDto update(EventRequest eventRequest, Long eventId, Authentication authentication);

    /**
     * Удаляет мероприятие по его идентификатору.
     *
     * @param id             идентификатор мероприятия
     * @param authentication данные авторизации
     * @return удалено ли мероприятие
     */
    boolean deleteById(Long id, Authentication authentication);

    /*
     * Методы, работающие с мероприятиями пользователя
     */

    /**
     * Возвращает список мероприятий, где пользователь является участником по аутентификации.
     *
     * @param authentication данные авторизации
     * @return список мероприятий
     */
    List<EventDto> getAllUserEventsByAuthentication(Authentication authentication);

    /**
     * Возвращает список мероприятий, где пользователь является участником по аутентификации
     * в интервал времени с даты начала интервала по дату конца интервала.
     *
     * @param fromDate дата начала интервала времени
     * @param toDate   дата конца интервала времени
     * @return список мероприятий
     */
    List<EventDto> getAllUserEventsByAuthAndDateInterval(
            Authentication authentication, LocalDate fromDate, LocalDate toDate);

    /*
     * Методы, работающие с участниками мероприятий
     */

    /**
     * Возращает список участников мероприятия.
     *
     * @param eventId идентификатор мероприятия
     * @return список участников
     */
    List<UserDto> getParticipantsByEventId(Long eventId);

    /**
     * Добавляет пользователя в список участников по идентификатору мероприятия и данным авторизации
     *
     * @param eventId        идентификатор мероприятия
     * @param authentication данные авторизации
     * @return добавлен ли пользователь в список участников
     */
    boolean addParticipantByAuthentication(Long eventId, Authentication authentication);

    /**
     * Удаляет пользователя из списка участников мероприятия по идентификатору мероприятия
     * и идентификатору пользователя. Доступно для организатора мероприятия или Администратора.
     *
     * @param eventId        идентификатор мероприятия
     * @param userId         идентификатор пользователя
     * @param authentication данные авторизации
     * @return удален ли пользователь из списка участников
     */
    boolean deleteParticipantByUserId(Long eventId, Long userId, Authentication authentication);

    /**
     * Удаляет пользователя из списка участников мероприятия по данным авторизации.
     *
     * @param eventId        идентификатор мероприятия
     * @param authentication данные авторизации
     * @return удален ли пользователь из списка участников
     */
    boolean deleteParticipantByAuthentication(Long eventId, Authentication authentication);

    /*
     * Методы, работающие с приглашениями на мероприятия
     */

    /**
     * Возращает список приглашенных по идентификатору мероприятия
     *
     * @param eventId        идентификатор мероприятия
     * @param authentication данные авторизации
     * @return список приглашенных
     */
    List<UserDto> getAllInvitationsByEventId(Long eventId, Authentication authentication);

    /**
     * Добавляет пользователя в список приглашенных по идентификатору мероприятия и идентификатору пользователя
     *
     * @param eventId        идентификатор мероприятия
     * @param userId         идентификатор пользователя
     * @param authentication данные авторизации
     * @return добавлен ли пользователь в список приглашенных
     */
    boolean addInvitationByUserId(Long eventId, Long userId, Authentication authentication);

    /**
     * Удаляет пользователя из списка приглашенных по идентификатору мероприятия и идентификатору пользователя.
     * Доступно для организатора мероприятия или Администратора.
     *
     * @param eventId        идентификатор мероприятия
     * @param userId         идентификатор пользователя
     * @param authentication данные авторизации
     * @return удален ли пользователь из списка приглашенных
     */
    boolean deleteInvitationByUserId(Long eventId, Long userId, Authentication authentication);

    /**
     * Принимает пользователем приглашение на участие в мероприятии.
     * Переносит пользователя из списка приглашенных в список участников мероприятия.
     *
     * @param eventId        идентификатор мероприятия
     * @param authentication данные авторизации
     * @return перенесен ли пользователь из списка приглашенных в список участников
     */
    boolean acceptInvitationByAuthentication(Long eventId, Authentication authentication);

    /**
     * Отклоняет пользователем приглашение на участие в мероприятии.
     * Удаляет пользователя из списка приглашенных по идентификатору мероприятия и данным авторизации пользователя.
     *
     * @param eventId        идентификатор мероприятия
     * @param authentication данные авторизации
     * @return удален ли пользователь из списка приглашенных
     */
    boolean rejectInvitationByAuthentication(Long eventId, Authentication authentication);
}
