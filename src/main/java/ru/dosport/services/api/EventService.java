package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.UserEventDto;

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
     * Возвращает все мероприятия
     *
     * @return список dto мероприятий
     */
    List<EventDto> getAllDto();

    /**
     * Сделать мероприятие приватным/публичным (только для организатора)
     *
     * @param eventId идентификатор мероприятия
     * @return true/false
     */
    boolean isPrivate(Long eventId, Authentication authentication);

    /**
     * Получить всех мероприятий за определенный интервал времени
     * @param from дата начала интервала времени, за который показывают мероприятия
     * @param to дата конца интервала времени
     * @return список мероприятий
     */
    List<EventDto> getAllDtoByTimeFromTo(LocalDateTime from, LocalDateTime to);

    /**
     * Получить список мероприятий для определенной площадки
     * @param sportGroundId идентификатор площадки, для которой получаем список мероприятий
     * @return список мероприятий
     */
    List<EventDto> getAllDtoBySportGroundId(Long sportGroundId);

    /**
     * Возвращает все мероприятия, имеющие определенные параметры поиска
     *
     * @return список dto мероприятий
     */
    List<EventDto> getAllDtoByParams(
            LocalDate fromDate, LocalDate toDate, Short sportTypeId, Long sportGroundId, Long organizerId);

    /**
     * Создать новое мероприятие
     *
     * @param eventRequest запрос, содержащий данные мероприятия
     * @param authentication данные авторизации
     * @return новое мероприятие, сохраненное в репозитории
     */
    EventDto save(EventRequest eventRequest, Authentication authentication);

    /**
     * Изменить данные мероприятия по его id
     *
     * @param eventRequest мероприятие с измененными данными
     * @param eventId индекс мероприятия
     * @param authentication данные авторизации
     */
    EventDto update(EventRequest eventRequest, Long eventId, Authentication authentication);

    /**
     * Удалить мероприятие по его идентификатору
     *
     * @param id идентификатор мероприятия
     * @param authentication данные авторизации
     * @return удалено ли мероприятие
     */
    boolean deleteById(Long id, Authentication authentication);

    /**
     * Проверяет существует ли сущность.
     * @param eventId идентификатор мероприятия
     * @return true/false
     */
    boolean exist(Long eventId);

//    /**
//     * Получить всех участников мероприятия.
//     * @param eventId идентификатор мероприятия
//     * @return список dto участников
//     */
//    List<MemberDto> getAllMembers(Long eventId);

    /**
     * Возращает список мероприятий по списку идентификатора
     * @param idList списк идентификаторов
     * @return список мероприятий
     */
    List<EventDto> findAllEventDtoById(List<Long> idList);

    /**
     * Возвращает мероприятия пользователя по аутентификации и за заданный интервал времени с заданной даты
     *
     * @param from дата начала интервала времени, за который показывают мероприятия
     * @param timeInterval интервал времени в днях, за который показывается мероприятия (1/7/31)
     */
    List<UserEventDto> getAllDtoByAuthTimeInterval(Authentication authentication, LocalDate from, byte timeInterval);


    /**
     * Возвращает мероприятия пользователя по аутентификации и за заданный интервал времени с заданной даты
     *
     * @param from дата начала интервала времени, за который показывают мероприятия
     * @param to дата конца интервала времени
     */
    List<UserEventDto> getAllDtoByAuthFromTo(Authentication authentication, LocalDate from, LocalDate to);

    /**
     * Возвращает все мероприятия пользователя по аутентификации
     *
     * @return список dto мероприятий
     */
    List<UserEventDto> getAllDtoByAuth(Authentication authentication);
}
