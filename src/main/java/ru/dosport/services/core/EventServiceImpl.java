package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.entities.Event;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.mappers.EventMapper;
import ru.dosport.repositories.EventRepository;
import ru.dosport.services.api.EventService;

import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

/**
 * Сервис пользователей
 */
//@Service
@RequiredArgsConstructor
public class EventServiceImpl { //implements EventService {

    /*
    // Необходимые сервисы и мапперы
    private final EventMapper eventMapper;

    // Необходимые репозитории
    private final EventRepository eventRepository;

    @Override
    public EventDto getDtoById(Long id) {
        return eventMapper.mapEntityToDto(findById(id));
    }

    @Override
    public List<EventDto> getAllDto() {
        return eventMapper.mapEntityToDto(eventRepository.findAll());
    }

    @Override
    public EventDto save(EventRequest eventRequest) {
        return eventMapper.mapEntityToDto(eventRepository.save(eventMapper.mapDtoToEntity(eventRequest)));
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
    */
}
