package ru.dosport.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.*;
import ru.dosport.services.api.EventMessageService;
import ru.dosport.services.api.EventService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Api(value = "/api/v1/events", tags = {"Контроллер Мероприятий"})
public class EventController {

    // Константы
    private final String DATA_TYPE = "application/json";
    private final String EVENT_ID = "Идентификатор мероприятия";
    private final String MESSAGE_ID = "Идентификатор сообщения";

    // Необходимые сервисы
    private final EventService eventService;
    private final EventMessageService eventMessageService;

    @ApiOperation(value = "Отображает данные всех мероприятий")
    @GetMapping
    public ResponseEntity<List<EventDto>> readAllEvent() {
        return ResponseEntity.ok(eventService.getAllDto());
    }

    @ApiOperation(value = "Отображает данные мероприятия по его индексу")
    @GetMapping(value = "/{id}", produces = DATA_TYPE)
    public ResponseEntity<EventDto> readEvent(@ApiParam(value = EVENT_ID) @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getDtoById(id));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Создает новое мероприятие")
    @PostMapping(produces = DATA_TYPE)
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventRequest eventRequest,
                                                Authentication authentication) {
        return ResponseEntity.ok(eventService.save(eventRequest, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Изменяет данные мероприятия")
    @PutMapping(value = "/{id}", produces = DATA_TYPE)
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto,
                                                @ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                Authentication authentication) {
        return ResponseEntity.ok(eventService.update(eventDto, id, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Удаляет мероприятие по его индексу")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                         Authentication authentication) {
        return eventService.deleteById(id, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Отображает данные всех участников мероприятия")
    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberDto>> readEventMember(@ApiParam(value = EVENT_ID) @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getAllMembers(id));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Добавляет участника в мероприятие")
    @PostMapping("/{id}/members")
    public ResponseEntity<?> addEventMember(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                            @RequestBody MemberRequest request) {
        return eventService.createEventMember(id, request) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<EventMessageDto>> readBoard(@ApiParam(value = EVENT_ID) @PathVariable Long id) {
        return ResponseEntity.ok(eventMessageService.getAllDtoByEventId(id));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{id}/messages")
    public ResponseEntity<EventMessageDto> createMessage(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                         @RequestBody EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.save(id, request, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping("/{id}/messages/{messageId}")
    public ResponseEntity<EventMessageDto> updateMessage(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                         @ApiParam(value = MESSAGE_ID) @PathVariable Long messageId,
                                                         EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.update(messageId, id, request, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{id}/messages/{messageId}")
    public ResponseEntity<EventMessageDto> deleteMessage(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                         @ApiParam(value = MESSAGE_ID) @PathVariable Long messageId,
                                                         Authentication authentication) {
        return eventMessageService.deleteById(id, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }
}
