package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.*;

import java.util.List;

public interface MessageEventService {

    /**
     * Найти мероприятие по его идентификатору
     *
     * @param id идентификатор мероприятия
     * @return мероприятие
     */
    MessageEventDto getDtoById(Long id);

    /**
     * Найти все мероприятия
     *
     * @return список мероприятий
     */
    List<MessageEventDto> getAllDto(Long eventId);

    /**
     * Создать новое мероприятие
     *
     * @return новое мероприятие, сохраненное в репозитории
     */
    MessageEventDto save(Long eventId, MessageEventRequest request, Authentication authentication);

    MessageEventDto update(Long messageId, Long eventId, MessageEventRequest request, Authentication authentication);

    /**
     * Удалить мероприятие по его идентификатору
     *
     * @param id идентификатор мероприятия
     * @param authentication
     * @return удалено ли мероприятие
     */
    boolean deleteById(Long id, Authentication authentication);
}
