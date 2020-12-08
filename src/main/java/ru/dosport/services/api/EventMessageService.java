package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.*;

import java.util.List;

public interface EventMessageService {

    /**
     * Возращает сообщение к мероприятию по его идентификатору
     * @param id идентификатор мероприятия
     * @return dto сообщения к мероприятию
     */
    EventMessageDto getDtoById(Long id);

    /**
     * Возращает все сообщения мероприятия
     * @param eventId идентификатор мероприятия
     * @return список dto сообщений мероприятия
     */
    List<EventMessageDto> getAllDtoByEventId(Long eventId);

    /**
     * Создаёт новое сообщение к мероприятию
     * @param eventId идентификатор мероприятия
     * @param request текст сообщения
     * @param authentication данные авторизации
     * @return dto нового сообщения к мероприятию, сохраненное в репозитории
     */
    EventMessageDto save(Long eventId, EventMessageRequest request, Authentication authentication);

    /**
     * Обновляет сообщение к мероприятию
     * @param messageId id сообщения
     * @param eventId id мероприятия
     * @param request запрос с новым текстом
     * @param authentication данные авторизации
     * @return dto нового сообщения к мероприятию, обновлённое в репозитории
     */
    EventMessageDto update(Long messageId, Long eventId, EventMessageRequest request, Authentication authentication);

    /**
     * Удаляет сообщение к мероприятию по его идентификатору
     *
     * @param messageId идентификатор сообщение
     * @param eventId идентификатор мероприятия
     * @param authentication данные авторизации
     * @return удалено ли сообщение
     */
    boolean deleteById(Long messageId, Long eventId, Authentication authentication);
}
