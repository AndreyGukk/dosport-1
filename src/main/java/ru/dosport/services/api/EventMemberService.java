package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.MemberDto;
import ru.dosport.dto.MemberRequest;

import java.util.List;

public interface EventMemberService {

    /**
     * Возращает список участников мероприятия.
     * @param eventId идентификатор мероприятия
     * @return список участников
     */
    List<MemberDto> readAllMembersByEventId(Long eventId);

    /**
     * Сохраняет или обновляет данные участника. Если участник не участвует в мероприятие - добавит в список и укажет
     * статус, иначе обновит статус участника.
     * @param request запрос на участие (статус)
     * @param eventId идентификатор мероприятия
     * @param authentication данные авторизации
     * @return dto участника
     */
    MemberDto saveOrUpdateMember(MemberRequest request, Long eventId, Authentication authentication);

    /**
     * Добавить участника мероприятия
     * @param eventId идентификатор мероприятия
     * @param request запрос, содержищий участника, идентификатор мероприятия, статус участника
     * @param authentication данные авторизации
     * @return dto участника
     */
    MemberDto addMember(MemberRequest request, Long eventId, Authentication authentication);

    /**
     * Удаляет участника.
     * @param userId идентификатор пользователя
     * @param eventId идентификатор мероприятия
     * @return true/false
     */
    boolean deleteMember(Long userId, Long eventId);

    /**
     * Возращает мероприятия в которых участвует пользователь.
     * @param userId идентификатор пользователя
     * @return список мероприятий
     */
    List<EventDto> readMemberEvent(Long userId);

    /**
     * Возращает участника(статус) пользоваетля.
     * @param eventId идентификатор мероприятия
     * @param userId идентификатор пользователя
     * @return dto участника
     */
    MemberDto readMember(Long eventId, Long userId);
}
