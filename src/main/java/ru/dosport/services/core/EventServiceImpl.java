package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.entities.Event;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Messages;
import ru.dosport.mappers.EventMapper;
import ru.dosport.repositories.EventRepository;
import ru.dosport.repositories.SportGroundRepository;
import ru.dosport.repositories.SportTypeRepository;
import ru.dosport.repositories.UserRepository;
import ru.dosport.services.api.EventService;

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

    // Необходимые репозитории
    private final EventRepository eventRepository;
    //todo: избавиться от лишних репозиториев
    private final SportTypeRepository sportTypeRepository;
    private final SportGroundRepository sportGroundRepository;
    private final UserRepository userRepository;

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
                .sportType(sportTypeRepository.findByTitle(eventRequest.getSportTypeTitle())
                        .orElseThrow(() -> new DataBadRequestException("Вид спорта не найден")))
                .sportGround(sportGroundRepository.findById(Long.valueOf(eventRequest.getIdSportGround()))
                        .orElseThrow(() -> new DataBadRequestException("Площадка не найдена")))
                .organizer(userRepository.findById(Long.valueOf(eventRequest.getIdOrganizer()))
                        .orElseThrow(() -> new DataBadRequestException("Пользователь не найден")))
                .build();
        if (eventRequest.getEndTimeEvent() != null) {
            event.setEndTime(LocalDateTime.parse(eventRequest.getEndTimeEvent()));
        }

        return eventMapper.mapEntityToDto(eventRepository.save(event));
    }

    @Override
    public EventDto update(EventDto eventDto, Long id) {
        return eventMapper.mapEntityToDto(eventRepository.save(eventMapper.update(findById(id), eventDto)));
    }

    @Override
    public boolean deleteById(Long id) {
        eventRepository.deleteById(id);
        return eventRepository.existsById(id);
    }

    // Найти пользователя по id
    private Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }
}
