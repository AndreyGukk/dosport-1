package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.UserEventDto;
import ru.dosport.entities.Event;
import ru.dosport.entities.SportGround;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.EventMapper;
import ru.dosport.repositories.EventRepository;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

/**
 * Сервис Мероприятий
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    // Необходимые зависимости
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final SportTypeService sportTypeService;
    private final SportGroundService sportGroundService;

    @Override
    public EventDto getDtoById(Long id) {
        return eventMapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<EventDto> findAllEventDtoById(List<Long> idList) {
        return eventMapper.mapEntityToDto(eventRepository.findAllById(idList));
    }

    @Override
    public List<EventDto> getAllDto() {
        return eventMapper.mapEntityToDto(eventRepository.findAll());
    }

    @Override
    public List<EventDto> getAllDtoByParams(LocalDate from, LocalDate to, Short sportTypeId, Long sportGroundId, Long organizerId) {
        return eventMapper.mapEntityToDto(
                eventRepository.findAllByParams(from, to, sportTypeId, sportGroundId, organizerId));
    }

    @Transactional
    @Override
    public EventDto save(EventRequest eventRequest, Authentication authentication) {
        Event event = Event.builder()
                .creationDateTime(LocalDateTime.now())
                .startDateTime(eventRequest.getStartDateTime())
                .sportType(sportTypeService.getSportTypeByTitle(eventRequest.getSportTypeTitle()))
                .sportGround(sportGroundService.getById(eventRequest.getSportGroundId()))
                .organizerId(userService.getIdByAuthentication(authentication))
                .build();
        if (eventRequest.getEndDateTime() != null) {
            event.setEndDateTime(eventRequest.getEndDateTime());
        }

        return eventMapper.mapEntityToDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventDto update(EventRequest request, Long eventId, Authentication authentication) {
        var event = findById(eventId);

        if (!(event.getOrganizerId() == userService.getIdByAuthentication(authentication))) {
            throw new AccessDeniedException("Пользователь не является организатором мероприятия");
        }

        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());

        if (!event.getSportType().getTitle().equals(request.getSportTypeTitle())) {
            event.setSportType(sportTypeService.getSportTypeByTitle(request.getSportTypeTitle()));
        }

        return eventMapper.mapEntityToDto(eventRepository.save(event));

    }

    @Transactional
    @Override
    public boolean deleteById(Long id, Authentication authentication) {
        Event event = findById(id);
        if (!(event.getOrganizerId() == userService.getIdByAuthentication(authentication))) {
            if (!Roles.hasAuthenticationRoleAdmin(authentication)) {
                throw new AccessDeniedException("Пользователь не является организатором мероприятия");
            }
        }
        eventRepository.deleteById(id);
        return !eventRepository.existsById(id);
    }

    @Override
    public boolean exist(Long eventId) {
        return eventRepository.existsById(eventId);
    }

    private Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    @Override
    public List<UserEventDto> getAllDtoByAuthTimeInterval(Authentication authentication, LocalDate from, byte timeInterval) {
        LocalDate to = from.plusDays(timeInterval);
        return getAllDtoByAuthFromTo(authentication, from, to);
    }

    @Override
    public List<UserEventDto> getAllDtoByAuthFromTo(Authentication authentication, LocalDate from, LocalDate to) {
        return eventMapper.mapUserEventToUserEventDto(eventRepository.findAllByUserIdAndTimeFromTo(
                userService.getIdByAuthentication(authentication), from, to));
    }

    @Override
    public List<UserEventDto> getAllDtoByAuth(Authentication authentication) {
        return eventMapper.mapUserEventToUserEventDto(eventRepository.findAllByUserId(
                userService.getIdByAuthentication(authentication)));
    }

    @Override
    public List<EventDto> getAllDtoByTimeFromTo(LocalDateTime from, LocalDateTime to) {
        return eventMapper.mapEntityToDto(eventRepository.findAllByTimeFromTo(from, to));
    }

    @Override
    public List<EventDto> getAllDtoBySportGroundId(Long sportGroundId) {
        return eventMapper.mapEntityToDto(eventRepository.findAllBySportGroundId(sportGroundId));
    }

    @Override
    public boolean isPrivate(Long eventId, Authentication authentication) {
        if (!Roles.hasAuthenticationRoleAdmin(authentication)) {
            throw new AccessDeniedException("Пользователь не является организатором мероприятия");
        } else {
            boolean isPrivate = isPrivate(eventId, authentication);
            return isPrivate;
        }
    }
}
