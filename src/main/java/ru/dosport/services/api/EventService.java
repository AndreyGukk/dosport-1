package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.MemberDto;

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
     * Найти мероприятие по его идентификатору
     *
     * @param id идентификатор мероприятия
     * @return мероприятие
     */
    EventDto getDtoById(Long id);

    /**
     * Найти все мероприятия
     *
     * @return список мероприятий
     */
    List<EventDto> getAllDto();

    /**
     * Создать новое мероприятие
     *
     * @param eventRequest запрос, содержащий данные мероприятия
     * @return новое мероприятие, сохраненное в репозитории
     */
    EventDto save(EventRequest eventRequest);

    /**
     * Изменить данные мероприятия по его id
     *
     * @param eventDto мероприятие с измененными данными
     * @param idEvent индекс мероприятия
     */
    EventDto update(EventDto eventDto, Long idEvent, Authentication authentication);

    /**
     * Удалить мероприятие по его идентификатору
     *
     * @param id идентификатор мероприятия
     * @param authentication
     * @return удалено ли мероприятие
     */
    boolean deleteById(Long id, Authentication authentication);

    List<MemberDto> getAllMembers(Long idEvent);

    MemberDto createEventMember(Long idEvent, MemberDto memberDto);
}
