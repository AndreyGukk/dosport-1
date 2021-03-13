package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.*;
import ru.dosport.services.api.EventMemberService;
import ru.dosport.services.api.EventMessageService;
import ru.dosport.services.api.EventService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static ru.dosport.helpers.MessageSwagger.PAR_EVENT_ID;
import static ru.dosport.helpers.MessageSwagger.PAR_MESSAGE_ID;
import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/events", produces = "application/json")
@Api(value = "/api/v1/events", tags = {"Контроллер Мероприятий"})
public class EventController {

    // Список необходимых зависимостей
    private final EventService eventService;
    private final EventMemberService memberService;
    private final EventMessageService eventMessageService;

    @ApiOperation(value = "Отображает данные всех мероприятий")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping
    public ResponseEntity<List<EventDto>> readAllEvent() {
        return ResponseEntity.ok(eventService.getAllDto());
    }

    @ApiOperation(value = "Отображает данные всех мероприятий площадки с заданными параметрами")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/")
    public ResponseEntity<List<EventDto>> readAllEventByParams(@RequestParam (required = false) LocalDate fromDate,
                                                               @RequestParam (required = false) LocalDate toDate,
                                                               @RequestParam (required = false) Short sportTypeId,
                                                               @RequestParam (required = false) Long sportGroundId,
                                                               @RequestParam (required = false) Long organizerId) {
        return ResponseEntity.ok(
                eventService.getAllDtoByParams(fromDate, toDate, sportTypeId, sportGroundId, organizerId));
    }

    @ApiOperation(value = "Отображает данные мероприятия по его индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDto> readEvent(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getDtoById(id));
    }

    @ApiOperation(value = "Создает новое мероприятие")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping()
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventRequest eventRequest,
                                                Authentication authentication) {
        return ResponseEntity.ok(eventService.save(eventRequest, authentication));
    }

    @ApiOperation(value = "Изменяет данные мероприятия")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDto> updateEvent(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long id,
                                                @Valid @RequestBody EventRequest eventRequest,
                                                Authentication authentication) {
        return ResponseEntity.ok(eventService.update(eventRequest, id, authentication));
    }

    @ApiOperation(value = "Удаляет мероприятие по его индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long id,
                                         Authentication authentication) {
        return eventService.deleteById(id, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     * Методы работающие с мероприятиями пользователя
     */

    @ApiOperation(value = "Отображает список мероприятий пользователя за период с ___ на ___ дней (1/7/31)")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping ("/calendar/interval")
    public ResponseEntity<List<UserEventDto>> readAllEventByAuthAndDate(Authentication authentication,
                                                                        @RequestParam  LocalDate from,
                                                                        @RequestParam  byte timeInterval){
        return ResponseEntity.ok(eventService.getAllDtoByAuthTimeInterval(authentication, from, timeInterval));
    }

    @ApiOperation(value = "Отображает список мероприятий пользователя за период с ___ по ___")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping ("/calendar/")
    public ResponseEntity<List<UserEventDto>> readAllEventByAuthAndDate(Authentication authentication,
                                                                        @RequestParam  LocalDate from,
                                                                        @RequestParam  LocalDate to){
        return ResponseEntity.ok(eventService.getAllDtoByAuthFromTo(authentication, from, to));
    }

    @ApiOperation(value = "Отображает список всех мероприятий пользователя")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping ("/calendar")
    public ResponseEntity<List<UserEventDto>> readAllEventByAuth(Authentication authentication){
        return ResponseEntity.ok(eventService.getAllDtoByAuth(authentication));
    }

    /**
     * Методы, работающие с участниками мероприятия
     */

    @ApiOperation(value = "Отображает участников по индентификатору мероприятия")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{eventId}/members")
    public ResponseEntity<List<MemberDto>> getAllMembers(@PathVariable Long eventId) {
        return ResponseEntity.ok(memberService.readAllMembersByEventId(eventId));
    }

    @ApiOperation(value = "Отображает мероприятия в которых пользователь является участником")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @GetMapping("/members/{userId}")
    public ResponseEntity<List<EventDto>> getMemberEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(memberService.readMemberEvent(userId));
    }

    @ApiOperation(value = "Отображает участника по индентификатору пользователя (Проверка статуса участника)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("{eventId}/members/{userId}")
    public ResponseEntity<MemberDto> getMemberEvents(@PathVariable Long eventId,
                                                     @PathVariable Long userId) {
        return ResponseEntity.ok(memberService.readMember(eventId, userId));
    }

    @ApiOperation(value = "Добавляет участника или обновляет статус участника, если он уже участвует")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER})
    @PostMapping("/{eventId}/members")
    public ResponseEntity<?> addMember(@PathVariable Long eventId,
                                       @Valid @RequestBody MemberRequest request,
                                       Authentication authentication) {
        return ResponseEntity.ok(memberService.saveOrUpdateMember(request, eventId, authentication));
    }

    @ApiOperation(value = "Удаляет участника из мероприятия по идентификатору пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @DeleteMapping("/{eventId}/members/{userId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long eventId,
                                          @PathVariable Long userId) {
        return memberService.deleteMember(userId, eventId) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     * Методы, работающие с сообщениями мероприятий
     */

    @ApiOperation(value = "Отоброжает данные всех сообщений мероприятия")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{eventId}/messages")
    public ResponseEntity<List<EventMessageDto>> readBoard(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId) {
        return ResponseEntity.ok(eventMessageService.getAllDtoByEventId(eventId));
    }

    @ApiOperation(value = "Добавляет сообщение к мероприятию")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{eventId}/messages")
    public ResponseEntity<EventMessageDto> createMessage(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
                                                         @Valid @RequestBody EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.save(eventId, request, authentication));
    }

    @ApiOperation(value = "Обновляет данные сообщения")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping("/{eventId}/messages/{messageId}")
    public ResponseEntity<EventMessageDto> updateMessage(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
                                                         @ApiParam(value = PAR_MESSAGE_ID) @PathVariable Long messageId,
                                                         @Valid @RequestBody EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.update(messageId, eventId, request, authentication));
    }

    @ApiOperation(value = "Удаляет сообщение")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{eventId}/messages/{messageId}")
    public ResponseEntity<EventMessageDto> deleteMessage(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
                                                         @ApiParam(value = PAR_MESSAGE_ID) @PathVariable Long messageId,
                                                         Authentication authentication) {
        return eventMessageService.deleteById(messageId, eventId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
