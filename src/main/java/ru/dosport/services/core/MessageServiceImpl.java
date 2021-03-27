package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.MessageDto;
import ru.dosport.dto.MessageRequest;
import ru.dosport.entities.Message;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.MessageMapper;
import ru.dosport.repositories.MessageRepository;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.MessageService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.util.List;

import static ru.dosport.helpers.InformationMessages.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    // Необходимые сервисы, мапперы и репозитории
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final EventService eventService;
    private final UserService userService;

    @Override
    public MessageDto getDtoById(Long messageId) {
        return messageMapper.mapEntityToDto(findMessageById(messageId));
    }

    @Override
    public List<MessageDto> getAllDtoByEventId(Long eventId) {
        return messageMapper.mapEntityToDto(messageRepository.findAllByEventId(eventId));
    }

    @Transactional
    @Override
    public MessageDto save(Long eventId, MessageRequest request, Authentication authentication) {
        checkIfEventExists(eventId);
        var user = userService.getByAuthentication(authentication);
        var message = Message.builder()
                .eventId(eventId)
                .user(user)
                .text(request.getText())
                .build();
        return messageMapper.mapEntityToDto(messageRepository.save(message));
    }

    @Transactional
    @Override
    public MessageDto update(Long eventId, Long messageId, MessageRequest request, Authentication authentication) {
        var message = findMessageById(messageId);
        checkIfUserIsMessageAuthorOrAdmin(message, authentication);
        if (message.getEventId().equals(eventId)) {
            message.setText(request.getText());
            return messageMapper.mapEntityToDto(messageRepository.save(message));
        }
        throw new DataBadRequestException(String.format(MESSAGE_DOES_NOT_BELONG_TO_EVENT, messageId, eventId));
    }

    @Transactional
    @Override
    public boolean deleteById(Long eventId, Long messageId, Authentication authentication) {
        checkIfEventExists(eventId);
        var message = findMessageById(messageId);
        checkIfUserIsMessageAuthorOrAdmin(message, authentication);
        messageRepository.delete(message);
        return !messageRepository.existsById(messageId);
    }

    /**
     * Проверяет является ли пользователь автором сообщения или администратором
     */
    private void checkIfUserIsMessageAuthorOrAdmin(Message message, Authentication authentication) {
        Long userId = userService.getIdByAuthentication(authentication);
        Long messageId = message.getUser().getId();
        if (!userId.equals(messageId) | !Roles.hasAuthenticationRoleAdmin(authentication)) {
            throw new DataBadRequestException(String.format(USER_IS_NOT_MESSAGE_AUTHOR, userId, messageId));
        }
    }

    /**
     * Возвращает сообщение по его идентификатору
     */
    private Message findMessageById(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow(
                () -> new DataNotFoundException(String.format(MESSAGE_NOT_FOUND_BY_ID, messageId))
        );
    }

    /**
     * Возвращает существует ли мероприятие по его идентификатору
     */
    private void checkIfEventExists(Long eventId) {
        if (!eventService.existById(eventId)) {
            throw new DataNotFoundException(String.format(EVENT_NOT_FOUND_BY_ID, eventId));
        }
    }
}
