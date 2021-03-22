package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.entities.Event;
import ru.dosport.entities.User;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.EventMapper;
import ru.dosport.mappers.UserMapper;
import ru.dosport.repositories.EventRepository;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.dosport.helpers.InformationMessages.*;

/**
 * Сервис Мероприятий
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    // Необходимые сервисы, мапперы и репозитории
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final SportTypeService sportTypeService;
    private final SportGroundService sportGroundService;

    // Паттерн форматирования даты и времени
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public EventDto getDtoById(Long id) {
        return eventMapper.mapEntityToDto(findEventById(id));
    }

    @Override
    public Event getById(Long id) {
        return findEventById(id);
    }

    @Override
    public List<EventDto> getAllDtoByTimeFromTo(LocalDateTime from, LocalDateTime to) {
        return eventMapper.mapEntityToDto(eventRepository.findAllByTimeFromTo(from, to));
    }

    @Override
    public List<EventDto> getAllDtoByParams(
            LocalDate from, LocalDate to, Short sportTypeId, Long sportGroundId, Long organizerId
    ) {
        return eventMapper.mapEntityToDto(
                eventRepository.findAllByParams(from, to, sportTypeId, sportGroundId, organizerId));
    }

    @Override
    public List<EventDto> getAllDtoBySportGroundId(Long sportGroundId) {
        return eventMapper.mapEntityToDto(eventRepository.findAllBySportGroundId(sportGroundId));
    }

    @Override
    public boolean existById(Long eventId) {
        return eventRepository.existsById(eventId);
    }

    @Transactional
    @Override
    public EventDto save(EventRequest eventRequest, Authentication authentication) {
        Event event = Event.builder()
                .creationDateTime(LocalDateTime.now())
                .startDateTime(LocalDateTime.parse(eventRequest.getStartDateTime(), formatter))
                .endDateTime(LocalDateTime.parse(eventRequest.getEndDateTime(), formatter))
                .sportType(sportTypeService.getSportTypeByTitle(eventRequest.getSportTypeTitle()))
                .sportGround(sportGroundService.getById(eventRequest.getSportGroundId()))
                .organizer(userService.getByAuthentication(authentication))
                .description(eventRequest.getDescription())
                .isPrivate(eventRequest.getIsPrivate())
                .price(eventRequest.getPrice())
                .maximumUsers(eventRequest.getMaximumUsers())
                .build();
        return eventMapper.mapEntityToDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventDto update(EventRequest eventRequest, Long eventId, Authentication authentication) {
        Event event = findEventById(eventId);
        checkUserIsOrganizerOrAdmin(event, authentication);
        eventMapper.update(event, eventRequest);
        if (!event.getSportType().getTitle().equals(eventRequest.getSportTypeTitle())) {
            event.setSportType(sportTypeService.getSportTypeByTitle(eventRequest.getSportTypeTitle()));
        }
        return eventMapper.mapEntityToDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public boolean deleteById(Long id, Authentication authentication) {
        checkUserIsOrganizerOrAdmin(findEventById(id), authentication);
        eventRepository.deleteById(id);
        return !eventRepository.existsById(id);
    }

    /*
     * Методы, работающие с мероприятиями пользователя
     */

    @Override
    public List<EventDto> getAllUserEventsByAuthentication(Authentication authentication) {
        return eventMapper.mapEntityToDto(userService.getByAuthentication(authentication).getEvents());
    }

    @Override
    public List<EventDto> getAllUserEventsByUserId(Long userId) {
        return eventMapper.mapEntityToDto(userService.getById(userId).getEvents());
    }

    @Override
    public List<EventDto> getAllUserEventsByAuthAndDateInterval(
            Authentication authentication,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return eventMapper.mapEntityToDto(
                userService.getByAuthentication(authentication).getEvents().stream()
                        .filter(a -> a.getStartDateTime().isAfter(ChronoLocalDateTime.from(fromDate)) &&
                                a.getEndDateTime().isBefore(ChronoLocalDateTime.from(toDate)))
                        .collect(Collectors.toList())
        );
    }

    /*
     * Методы, работающие с участниками мероприятий
     */

    @Override
    public List<UserDto> getParticipantsByEventId(Long eventId) {
        return userMapper.mapEntityToDto(findEventById(eventId).getParticipants());
    }

    @Transactional
    @Override
    public boolean addParticipantByAuthentication(Long eventId, Authentication authentication) {
        Event event = findEventById(eventId);
        if (!event.getIsPrivate()) {
            User user = userService.getByAuthentication(authentication);
            Set<User> participants = event.getParticipants();
            participants.add(user);
            event.setUsersAmount((short)participants.size());
            return eventRepository.save(event).getParticipants().contains(user);
        } else {
            throw new DataBadRequestException(CANNOT_PARTICIPATE_IN_PRIVATE_EVENT);
        }
    }

    @Transactional
    @Override
    public boolean deleteParticipantByUserId(Long eventId, Long userId, Authentication authentication) {
        Event event = findEventById(eventId);
        checkUserIsOrganizerOrAdmin(event, authentication);
        return checkUserAndDeleteFromParticipants(event, userService.getById(userId));
    }

    @Transactional
    @Override
    public boolean deleteParticipantByAuthentication(Long eventId, Authentication authentication) {
        Event event = findEventById(eventId);
        return checkUserAndDeleteFromParticipants(event, userService.getByAuthentication(authentication));
    }

    /*
     * Методы, работающие с приглашениями на мероприятия
     */

    @Override
    public List<UserDto> getAllInvitationsByEventId(Long eventId, Authentication authentication) {
        return userMapper.mapEntityToDto(findEventById(eventId).getInvitations());
    }

    @Transactional
    @Override
    public boolean addInvitationByUserId(Long eventId, Long userId, Authentication authentication) {
        Event event = findEventById(eventId);
        User user = userService.getById(userId);
        checkUserIsParticipantOrOrganizer(event, user);
        event.getInvitations().add(user);
        return eventRepository.save(event).getInvitations().contains(user);
    }

    @Transactional
    @Override
    public boolean deleteInvitationByUserId(Long eventId, Long userId, Authentication authentication) {
        Event event = findEventById(eventId);
        checkUserIsOrganizerOrAdmin(event, authentication);
        return checkUserAndDeleteFromInvitations(event, userService.getById(userId));
    }

    @Transactional
    @Override
    public boolean acceptInvitationByAuthentication(Long eventId, Authentication authentication) {
        Event event = findEventById(eventId);
        User user = userService.getByAuthentication(authentication);
        if (event.getInvitations().contains(user)) {
            event.getInvitations().remove(user);
            event.getParticipants().add(user);
            return eventRepository.save(event).getParticipants().contains(user);
        }
        return false;
    }

    @Transactional
    @Override
    public boolean rejectInvitationByAuthentication(Long eventId, Authentication authentication) {
        Event event = findEventById(eventId);
        return checkUserAndDeleteFromInvitations(event, userService.getByAuthentication(authentication));
    }

    /**
     * Возвращает мероприятие по его идентификатору
     */
    private Event findEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    /**
     * Возвращает является ли пользователь организатором мероприятия или Администратором
     */
    private void checkUserIsOrganizerOrAdmin(Event event, Authentication authentication) {
        if (!(userService.getIdByAuthentication(authentication).equals(event.getOrganizer().getId()) |
                Roles.hasAuthenticationRoleAdmin(authentication))) {
            throw new DataBadRequestException(USER_IS_NOT_EVENT_ORGANIZER);
        }
    }

    /**
     * Возвращает является ли пользователь организатором или участником мероприятия
     */
    private void checkUserIsParticipantOrOrganizer(Event event, User user) {
        if (!(user.getId().equals(event.getOrganizer().getId()) | event.getParticipants().contains(user))) {
            throw new DataBadRequestException(USER_IS_NOT_EVENT_ORGANIZER);
        }
    }

    /**
     * Возвращает содержится ли пользователь в списке участников и удаляет из списка при необходимости
     *
     * @return удален ли пользователь из списка участников
     */
    private boolean checkUserAndDeleteFromParticipants(Event event, User user) {
        if (event.getParticipants().contains(user)) {
            Set<User> participants = event.getParticipants();
            participants.remove(user);
            event.setUsersAmount((short)participants.size());
            return !eventRepository.save(event).getParticipants().contains(user);
        } else {
            return true;
        }
    }

    /**
     * Возвращает содержится ли пользователь в списке приглашенных и удаляет из списка при необходимости
     *
     * @return удален ли пользователь из списка приглашенных
     */
    private boolean checkUserAndDeleteFromInvitations(Event event, User user) {
        if (event.getInvitations().contains(user)) {
            event.getInvitations().remove(user);
            return !eventRepository.save(event).getInvitations().contains(user);
        } else {
            return true;
        }
    }
}
