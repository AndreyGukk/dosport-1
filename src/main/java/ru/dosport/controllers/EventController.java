package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.services.api.EventService;

import javax.validation.Valid;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

/**
 * Контроллер Мероприятий.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class EventController {

    // Тип данных
    private final String DATA_TYPE = "application/json";

    /*
    // Необходимые сервисы
    private final EventService eventService;

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Отображает данные мероприятия по его индексу")
    @GetMapping(value = "/{id}", produces = DATA_TYPE)
    public ResponseEntity<EventDto> readEvent(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(eventService.getDtoById(id), HttpStatus.OK);
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Изменяет данные мероприятия")
    @PostMapping(value = "/{id}", produces = DATA_TYPE)
    public ResponseEntity<EventDto> updateEvent(@Valid @RequestBody EventDto eventDto,
                                                @PathVariable(name = "id") Long id,
                                                Authentication authentication) {
        return new ResponseEntity<>(eventService.update(eventDto, id), HttpStatus.OK);
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Создает новое мероприятие")
    @PostMapping(value = "/create", produces = DATA_TYPE)
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        return new ResponseEntity<>(eventService.save(eventRequest), HttpStatus.OK);
    }
    */
}
