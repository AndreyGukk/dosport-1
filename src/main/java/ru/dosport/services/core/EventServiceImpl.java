package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;
import ru.dosport.specifications.EventSearchCriteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Patterns.EVENT_SORT_BY_DATE;
import static ru.dosport.helpers.Patterns.PAGE_SIZE;
import static ru.dosport.specifications.EventSpecifications.eventHasSearchCriteria;

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

    @Override
    public EventDto getDtoById(Long id) {
        return eventMapper.mapEntityToDto(findEventById(id));
    }

    @Override
    public List<EventDto> getAllDtoBySearchCriteria(EventSearchCriteria searchCriteria, Integer pageNumber) {
        return eventMapper.mapEntityToDto(eventRepository
                .findAll(eventHasSearchCriteria(searchCriteria), getPageable(pageNumber)).toList());
    }

    @Override
    public List<EventDto> getAllDtoBySportGroundId(Long sportGroundId, Integer pageNumber) {
        return eventMapper.mapEntityToDto(eventRepository.findAllBySportGround(sportGroundId, getPageable(pageNumber)));
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
                .startDateTime(eventRequest.getStartDateTime())
                .endDateTime(eventRequest.getEndDateTime())
                .sportType(sportTypeService.getSportTypeByTitle(eventRequest.getSportTypeTitle()))
                .sportGround(eventRequest.getSportGroundId())
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
        checkUserIsEventOrganizerOrAdmin(event, authentication);
        eventMapper.update(event, eventRequest);
        if (!event.getSportType().getTitle().equals(eventRequest.getSportTypeTitle())) {
            event.setSportType(sportTypeService.getSportTypeByTitle(eventRequest.getSportTypeTitle()));
        }
        return eventMapper.mapEntityToDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public boolean deleteById(Long id, Authentication authentication) {
        checkUserIsEventOrganizerOrAdmin(findEventById(id), authentication);
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
        checkUserIsEventOrganizerOrAdmin(event, authentication);
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
        checkIfUserIsEventParticipantOrOrganizer(event, user);
        event.getInvitations().add(user);
        return eventRepository.save(event).getInvitations().contains(user);
    }

    @Transactional
    @Override
    public boolean deleteInvitationByUserId(Long eventId, Long userId, Authentication authentication) {
        Event event = findEventById(eventId);
        checkUserIsEventOrganizerOrAdmin(event, authentication);
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
    private void checkUserIsEventOrganizerOrAdmin(Event event, Authentication authentication) {
        Long userId = userService.getIdByAuthentication(authentication);
        if (!(userId.equals(event.getOrganizer().getId()) |
                !Roles.hasAuthenticationRoleAdmin(authentication))) {
            throw new DataBadRequestException(String.format(USER_IS_NOT_EVENT_ORGANIZER, userId, event.getId()));
        }
    }

    /**
     * Проверяет является ли пользователь организатором или участником мероприятия
     */
    private void checkIfUserIsEventParticipantOrOrganizer(Event event, User user) {
        Long userId = user.getId();
        if (!(userId.equals(event.getOrganizer().getId()) |
                !event.getParticipants().contains(user))) {
            throw new DataBadRequestException(String.format(USER_IS_NOT_EVENT_ORGANIZER, userId, event.getId()));
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

    /**
     * Возвращает объект сортировки страниц поиска мероприятий
     *
     * @return объект сортировки
     */
    private Pageable getPageable(Integer pageNumber) {
        return pageNumber != null ?
                PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.ASC, EVENT_SORT_BY_DATE) :
                PageRequest.of(0, PAGE_SIZE, Sort.Direction.ASC, EVENT_SORT_BY_DATE);
    }
}

