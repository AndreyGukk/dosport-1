package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.MessageDto;
import ru.dosport.dto.MessageRequest;
import ru.dosport.entities.Message;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.EventMapper;
import ru.dosport.mappers.MessageMapper;
import ru.dosport.repositories.MessageRepository;
import ru.dosport.services.api.MessageService;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.util.List;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    // Необходимые сервисы, мапперы и репозитории
    private final MessageRepository messageRepository;
    private final EventMapper eventMapper;
    private final MessageMapper messageMapper;
    private final EventService eventService;
    private final UserService userService;

    @Override
    public MessageDto getDtoById(Long messageId) {
        return messageMapper.mapEntityToDto(findById(messageId));
    }

    @Override
    public List<MessageDto> getAllDtoByEventId(Long eventId) {
        return messageMapper.mapEntityToDto(messageRepository.findAllByEventId(eventId));
    }

    @Transactional
    @Override
    public MessageDto save(Long eventId, MessageRequest request, Authentication authentication) {
        checkExistEvent(eventId);

        var event = eventMapper.mapDtoToEntity(eventService.getDtoById(eventId));
        var user = userService.getByAuthentication(authentication);
        var message = Message.builder()
                .event(event)
                .user(user)
                .text(request.getText())
                .build();
        return messageMapper.mapEntityToDto(messageRepository.save(message));
    }

    @Override
    public MessageDto update(Long eventId, Long messageId, MessageRequest request, Authentication authentication) {
        checkExistEvent(eventId);

        var event = eventService.getDtoById(eventId);
        var message = findById(messageId);

        if (message.getEvent().getId().equals(event.getEventId())) {
            message.setText(request.getText());
            return messageMapper.mapEntityToDto(messageRepository.save(message));
        }

        throw new DataBadRequestException("Не правильно указано мероприятие");
    }

    @Transactional
    @Override
    public boolean deleteById(Long eventId, Long messageId, Authentication authentication) {
        checkExistEvent(eventId);
        var message = findById(messageId);

        if (!message.getUser().equals(userService.getIdByAuthentication(authentication))) {
            if (!Roles.hasAuthenticationRoleAdmin(authentication)) {
                throw new AccessDeniedException("Пользователь не является автором сообщения");
            }
        }

        messageRepository.delete(message);
        return !messageRepository.existsById(messageId);
    }

    private Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id))
        );
    }

    private void checkExistEvent(Long eventId) {
        if (!eventService.existById(eventId)) {
            throw new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, eventId));
        }
    }
}
