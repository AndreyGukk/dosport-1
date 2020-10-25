package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.MemberDto;
import ru.dosport.entities.Event;
import ru.dosport.entities.EventMember;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Messages;
import ru.dosport.mappers.EventMapper;
import ru.dosport.mappers.MemberMapper;
import ru.dosport.repositories.EventRepository;
import ru.dosport.repositories.MemberRepository;
import ru.dosport.repositories.UserRepository;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.SportTypeService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

/**
 * Сервис пользователей
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    // Необходимые сервисы и мапперы
    private final EventMapper eventMapper;
    private final MemberMapper memberMapper;

    // Необходимые репозитории
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    //todo: избавиться от лишних репозиториев
    private final UserRepository userRepository;

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
    public EventDto save(EventRequest eventRequest) {
        Event event = Event.builder()
                .date(eventRequest.getDateEvent())
                .startTime(LocalDateTime.parse(eventRequest.getStartTimeEvent()))
                .sportType(sportTypeService.getSportTypeByTitle(eventRequest.getSportTypeTitle()))
                .sportGround(sportGroundService.getSportGroundById(Long.valueOf(eventRequest.getIdSportGround())))
                .organizer(userRepository.findById(Long.valueOf(eventRequest.getIdOrganizer()))
                        .orElseThrow(() -> new DataBadRequestException("Пользователь не найден")))
                .build();
        if (eventRequest.getEndTimeEvent() != null) {
            event.setEndTime(LocalDateTime.parse(eventRequest.getEndTimeEvent()));
        }

        return eventMapper.mapEntityToDto(eventRepository.save(event));
    }

    @Override
    public EventDto update(EventDto eventDto, Long idEvent, Authentication authentication) {
        if (authentication != null) {
            Event event = findById(idEvent);
            //TODO: проверка организатора по authentication
            if (!event.getOrganizer().getId().equals(eventDto.getIdOrganizer())) {
                throw new AccessDeniedException("Пользователь не является организатором мероприятия");
            }

            return eventMapper.mapEntityToDto(eventRepository.save(eventMapper.update(findById(idEvent), eventDto)));
        } else {
            throw new AccessDeniedException(Messages.ACCESS_DENIED);
        }
    }

    @Override
    public boolean deleteById(Long id, Authentication authentication) {
        Event event = findById(id);
        //TODO: проверка организатора по authentication
//        if (event.getOrganizer().getId().equals(authentication))
        eventRepository.deleteById(id);
        return eventRepository.existsById(id);
    }

    @Override
    public List<MemberDto> getAllMembers(Long idEvent) {
        return memberMapper.mapEntityToDto(memberRepository.findAllByEventId(idEvent));
    }

    @Override
    public MemberDto createEventMember(Long idEvent, MemberDto memberDto) {
        if (memberDto.getIdEvent().equals(idEvent)) {
            throw new DataBadRequestException("Не правильно указано мероприятие");
        }

        Event event = findById(idEvent);
        EventMember member = memberMapper.mapDtoToEntity(memberDto);
        member.setEvent(event);

        return memberMapper.mapEntityToDto(memberRepository.save(member));
    }

    // Найти пользователя по id
    private Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }
}
