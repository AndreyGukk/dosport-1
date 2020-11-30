package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.MemberDto;
import ru.dosport.dto.MemberRequest;

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
     * @param eventDto мероприятие с измененными данными
     * @param eventId индекс мероприятия
     * @param authentication данные авторизации
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

    /**
     * Возращает список мероприятйи по списку идентификатора
     * @param idList списк идентификаторов
     * @return список мероприятий
     */
    List<EventDto> findAllEventDtoById(List<Long> idList);
}
