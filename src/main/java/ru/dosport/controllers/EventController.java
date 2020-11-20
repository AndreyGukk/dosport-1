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
import java.time.LocalDate;
import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/events", produces = "application/json")
@Api(value = "/api/v1/events", tags = {"Контроллер Мероприятий"})
public class EventController {

    // Константы
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
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDto> readEvent(@ApiParam(value = EVENT_ID) @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getDtoById(id));
    }

    @ApiOperation(value = "Создает новое мероприятие")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping()
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventRequest eventRequest,
                                                Authentication authentication) {
        return ResponseEntity.ok(eventService.save(eventRequest, authentication));
    }

    @ApiOperation(value = "Изменяет данные мероприятия")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto,
                                                @ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                Authentication authentication) {
        return ResponseEntity.ok(eventService.update(eventDto, id, authentication));
    }

    @ApiOperation(value = "Удаляет мероприятие по его индексу")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                         Authentication authentication) {
        return eventService.deleteById(id, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Отображает данные всех участников мероприятия")
    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberDto>> readEventMember(@ApiParam(value = EVENT_ID) @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getAllMembers(id));
    }

    @ApiOperation(value = "Добавляет участника в мероприятие")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{id}/members")
    public ResponseEntity<?> addEventMember(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                            @RequestBody MemberRequest request) {
        return eventService.createEventMember(id, request) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Отоброжает данные всех сообщений мероприятия")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<EventMessageDto>> readBoard(@ApiParam(value = EVENT_ID) @PathVariable Long id) {
        return ResponseEntity.ok(eventMessageService.getAllDtoByEventId(id));
    }

    @ApiOperation(value = "Добавляет сообщение к мероприятию")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{id}/messages")
    public ResponseEntity<EventMessageDto> createMessage(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                         @RequestBody EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.save(id, request, authentication));
    }

    @ApiOperation(value = "Обновляет данные сообщения")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping("/{id}/messages/{messageId}")
    public ResponseEntity<EventMessageDto> updateMessage(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                         @ApiParam(value = MESSAGE_ID) @PathVariable Long messageId,
                                                         EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.update(messageId, id, request, authentication));
    }

    @ApiOperation(value = "Удаляет сообщение")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{id}/messages/{messageId}")
    public ResponseEntity<EventMessageDto> deleteMessage(@ApiParam(value = EVENT_ID) @PathVariable Long id,
                                                         @ApiParam(value = MESSAGE_ID) @PathVariable Long messageId,
                                                         Authentication authentication) {
        return eventMessageService.deleteById(messageId, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Отображает список мероприятий пользователя за период с ___ на ___ дней (1/7/31")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping ("/calendar")
    public ResponseEntity<List<UserEventDto>> readAllEventByAuth(Authentication authentication, @RequestParam  LocalDate from, @RequestParam  byte timeInterval){
        return ResponseEntity.ok(eventService.getAllDtoByAuthTimeInterval(authentication, from, timeInterval));
    }

    @ApiOperation(value = "Отображает список мероприятий пользователя за период с ___ по ___")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping ("/calendar")
    public ResponseEntity<List<UserEventDto>> readAllEventByAuth(Authentication authentication, @RequestParam  LocalDate from, @RequestParam  LocalDate to){
        return ResponseEntity.ok(eventService.getAllDtoByAuthFromTo(authentication, from, to));
    }

}
