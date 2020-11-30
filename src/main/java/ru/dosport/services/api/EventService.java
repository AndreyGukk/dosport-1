package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.MemberDto;
import ru.dosport.dto.MemberRequest;
import ru.dosport.dto.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис Мероприятий
 */
public interface EventService {

    /*
     * СОГЛАШЕНИЕ О НАИМЕНОВАНИИ МЕТОДОВ СЕРВИСОВ
     * Event getById(Long id) найти объект по параметру
     * EventDto getDtoById(Long id) найти Dto объект по параметру
     * List<Event> getAll() найти все объекты
     * List<EventDto> getAllDto() найти все Dto объекты
     * List<EventDto> getAllDtoByUser(UserDto userDto) найти все Dto объекты по параметру
     * EventDto update(EventDto EventDto) изменить объект
     * EventDto save(EventDto EventDto) сохранить объект
     * List<EventDto> saveAllDto(List<EventDto> EventDtoList) сохранить список объектов
     * void delete(EventDto EventDto) удалить конкретный объект
     * void deleteById(Long id) удалить объект по параметру
     * void deleteAll(List<EventDto> EventDtoList) удалить список объектов
     */

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
     * Создать новое мероприятие
     *
     * @param eventRequest запрос, содержащий данные мероприятия
     * @return новое мероприятие, сохраненное в репозитории
     */
    EventDto save(EventRequest eventRequest, Authentication authentication);

    /**
     * Изменить данные мероприятия по его id
     *
     * @param eventDto мероприятие с измененными данными
     * @param eventId индекс мероприятия
     */
    EventDto update(EventDto eventDto, Long eventId, Authentication authentication);

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
     * Возращает список мероприятйи по списку идентификатора
     * @param idList списк идентификаторов
     * @return список мероприятий
     */
    List<EventDto> findAllEventDtoById(List<Long> idList);

//    /**
//     * Добавить участника мероприятия
//     * @param eventId идентификатор мероприятия
//     * @param request запрос, содержищий участника, идентификатор мероприятия, статус участника
//     * @return dto участника
//     */
//    MemberDto createEventMember(Long eventId, MemberRequest request);

    /**
     * Возвращает мероприятия пользователя по аутентификации и за заданный интервал в ремени с заданной даты
     *
     * @param from дата начала интервала времени, за который показывают мероприятия
     * @param timeInterval интервал времени в днях, за который показывается мероприятия (1/7/31)
     */
    List<UserEventDto> getAllDtoByAuthTimeInterval(Authentication authentication, LocalDate from, byte timeInterval);


    /**
     * Возвращает мероприятия пользователя по аутентификации и за заданный интервал в ремени с заданной даты
     *
     * @param from дата начала интервала времени, за который показывают мероприятия
     * @param to дата конца интервала времени
     */
    List<UserEventDto> getAllDtoByAuthFromTo(Authentication authentication, LocalDate from, LocalDate to);

}
