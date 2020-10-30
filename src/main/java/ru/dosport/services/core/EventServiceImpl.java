package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.*;
import ru.dosport.entities.Event;
import ru.dosport.entities.EventMember;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Messages;
import ru.dosport.mappers.EventMapper;
import ru.dosport.mappers.EventMemberMapper;
import ru.dosport.repositories.EventRepository;
import ru.dosport.repositories.MemberRepository;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

/**
 * Сервис пользователей
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    // Необходимые сервисы и мапперы
    private final EventMapper eventMapper;
    private final EventMemberMapper memberMapper;

    // Необходимые репозитории
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    private final UserService userService;
    private final SportTypeService sportTypeService;
    private final SportGroundService sportGroundService;

    @Override
    public EventDto getDtoById(Long id) {
        return eventMapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<EventDto> getAllDto() {
        return eventMapper.mapEntityToDto(eventRepository.findAll());
    }

    @Transactional
    @Override
    public EventDto save(EventRequest eventRequest, Authentication authentication) {
        Event event = Event.builder()
                .date(eventRequest.getDateEvent())
                .startTime(eventRequest.getStartTimeEvent())
                .sportType(sportTypeService.getSportTypeByTitle(eventRequest.getSportTypeTitle()))
                .sportGround(sportGroundService.getSportGroundById(Long.valueOf(eventRequest.getSportGroundId())))
                .organizerId(userService.getIdByAuthentication(authentication))
                .build();
        if (eventRequest.getEndTimeEvent() != null) {
            event.setEndTime(eventRequest.getEndTimeEvent());
        }

        return eventMapper.mapEntityToDto(eventRepository.save(event));
    }

    @Override
    public EventDto update(EventDto eventDto, Long eventId, Authentication authentication) {
        if (authentication != null) {
            Event event = findById(eventId);

            if (!event.getOrganizerId().equals(userService.getIdByAuthentication(authentication))) {
                throw new AccessDeniedException("Пользователь не является организатором мероприятия");
            }

            return eventMapper.mapEntityToDto(eventRepository.save(eventMapper.update(findById(eventId), eventDto)));
        } else {
            throw new AccessDeniedException(Messages.ACCESS_DENIED);
        }
    }

    @Override
    public boolean deleteById(Long id, Authentication authentication) {
        Event event = findById(id);
        //TODO: нужна проверка authentication на роль админа (через сервис user)
        if (!event.getOrganizerId().equals(userService.getIdByAuthentication(authentication))) {
            throw new AccessDeniedException("Пользователь не является организатором мероприятия");
        }
        eventRepository.deleteById(id);
        return eventRepository.existsById(id);
    }

    @Override
    public List<MemberDto> getAllMembers(Long eventId) {
        //TODO: метод получение определённых пользователей ( getUserDtos(List<Long> idList) )
        List<MemberDto> memberDtoList;
        List<EventMember> members = memberRepository.findAllByEventId(eventId);

        memberDtoList = members.stream()
                .map(m -> memberMapper.mapEntityToDto(m, userService.getDtoById(m.getUserId())))
                .collect(Collectors.toList());

        return memberDtoList;
    }

    @Override
    public MemberDto createEventMember(Long eventId, MemberRequest request) {
        if (eventId.equals(request.getEvenId())) {
            throw new DataBadRequestException("Не правильно указано мероприятие");
        }
        //TODO: метод проверки существования пользователя по id (user service)
        UserDto user = userService.getDtoById(request.getUserId());
        Event event = findById(eventId);
        EventMember member = EventMember.builder()
                .event(event)
                .userId(request.getUserId())
                .status(request.getUserStatus())
                .build();
        return memberMapper.mapEntityToDto(memberRepository.save(member), user);
    }

    private Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }
}
