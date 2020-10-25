package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.MemberDto;
import ru.dosport.services.api.EventService;

import javax.validation.Valid;

import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

/**
 * Контроллер Мероприятий.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> readAllEvent() {
        return ResponseEntity.ok(eventService.getAllDto());
    }

    @ApiOperation(value = "Отображает данные мероприятия по его индексу")
    @GetMapping(value = "/{id}", produces = DATA_TYPE)
    public ResponseEntity<EventDto> readEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getDtoById(id));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Создает новое мероприятие")
    @PostMapping(produces = DATA_TYPE)
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.save(eventRequest));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Изменяет данные мероприятия")
    @PutMapping(value = "/{id}", produces = DATA_TYPE)
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto, @PathVariable Long id,
                                                Authentication authentication) {
        return ResponseEntity.ok(eventService.update(eventDto, id, authentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id, Authentication authentication) {
        return eventService.deleteById(id, authentication) ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/members/")
    public ResponseEntity<List<MemberDto>> readEventMember(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getAllMembers(id));
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<?> addMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        return eventService.createEventMember(id, memberDto) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
