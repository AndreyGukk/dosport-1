package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventMessageDto;
import ru.dosport.dto.MessageEventRequest;
import ru.dosport.entities.Event;
import ru.dosport.entities.MessageEvent;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.EventMapper;
import ru.dosport.mappers.EventMessageMapper;
import ru.dosport.repositories.EventMessageRepository;
import ru.dosport.services.api.EventMessageService;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class EventMessageServiceImpl implements EventMessageService {

    private final EventMessageRepository messageRepository;

    private final EventMapper eventMapper;
    private final EventMessageMapper messageMapper;

    private final EventService eventService;
    private final UserService userService;

    @Override
    public EventMessageDto getDtoById(Long id) {
        return messageMapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<MessageEventDto> getAllDto(Long id) {
        return messageMapper.mapEntityToDto(messageRepository.findAllByEventId(id));
    }

    @Transactional
    @Override
    public EventMessageDto save(Long idEvent, MessageEventRequest request, Authentication authentication) {
        Event event = eventMapper.mapDtoToEntity(eventService.getDtoById(idEvent));
        MessageEvent message = MessageEvent.builder()
                .event(event)
                .userId(userService.getIdByAuthentication(authentication))
                .userName(request.getUserName())
                .text(request.getText())
                .build();
        return messageMapper.mapEntityToDto(messageRepository.save(message));
    }

    @Override
    public EventMessageDto update(Long messageId , Long eventId, MessageEventRequest request, Authentication authentication) {
        EventDto event = eventService.getDtoById(eventId);
        EventMessage message = findById(messageId);

        if (message.getEvent().getId().equals(event.getEventId())) {
            message.setText(request.getText());
            return messageMapper.mapEntityToDto(messageRepository.save(message));
        }

        throw new DataBadRequestException("Не правильно указано мероприятие");
    }

    @Transactional
    @Override
    public boolean deleteById(Long id, Authentication authentication) {
        EventMessage message = findById(id);

        if (!message.getUserId().equals(userService.getIdByAuthentication(authentication))) {
            if (!Roles.hasAuthenticationRoleAdmin(authentication)) {
                throw new AccessDeniedException("Пользователь не является автором сообщения");
            }
        }

        messageRepository.delete(message);
        return messageRepository.existsById(id);
    }

    private EventMessage findById(Long id) {
        return messageRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id))
        );
    }
}
