package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.*;
import ru.dosport.services.api.EventService;
import ru.dosport.services.api.MessageService;
import ru.dosport.specifications.EventSearchCriteria;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Patterns.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;
import static ru.dosport.helpers.SwaggerMessages.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/events", produces = "application/json")
@Api(value = "/api/v1/events", tags = {"Контроллер Мероприятий"})
public class EventController {

    // Список необходимых зависимостей
    private final EventService eventService;
    private final MessageService messageService;

    @ApiOperation(value = "Отображает данные всех мероприятий с заданными критериями поиска \n" +
            "по 15 объектов на каждой странице поиска.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("")
    public ResponseEntity<List<EventDto>> readAllEventsBySearchCriteria(
            @ApiParam(value = PAR_CREATION_DATE_FROM, example = LOCAL_DATE_TIME_EXAMPLE_1) @RequestParam(required = false)
                @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime creationDateTimeFrom,
            @ApiParam(value = PAR_CREATION_DATE_TO, example = LOCAL_DATE_TIME_EXAMPLE_2) @RequestParam(required = false)
                @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime creationDateTimeTo,
            @ApiParam(value = PAR_START_DATE) @RequestParam(required = false)
                @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime startDateTimeFrom,
            @ApiParam(value = PAR_END_DATE) @RequestParam(required = false)
                @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime endDateTimeTo,
            @ApiParam(value = PAR_SPORT_TYPE_ID) @RequestParam(required = false) Short sportTypeId,
            @ApiParam(value = PAR_SPORTGROUND_ID) @RequestParam(required = false) Long sportGroundId,
            @ApiParam(value = PAR_ORGANIZER_ID) @RequestParam(required = false) Long organizerId,
            @ApiParam(value = PAR_PRIVATE) @RequestParam(required = false) Boolean isPrivate,
            @ApiParam(value = PAR_PRICE_MIN) @RequestParam(required = false) Integer minPrice,
            @ApiParam(value = PAR_PRICE_MAX) @RequestParam(required = false) Integer maxPrice,
            @ApiParam(value = PAR_PAGE_NUMBER, defaultValue = "0") @RequestParam(required = false, defaultValue = "0") Integer pageNumber
    ) {
        return ResponseEntity.ok(eventService
                .getAllDtoBySearchCriteria(
                        EventSearchCriteria.builder()
                        .creationDateTimeFrom(creationDateTimeFrom)
                        .creationDateTimeTo(creationDateTimeTo)
                        .startDateTimeFrom(startDateTimeFrom)
                        .endDateTimeTo(endDateTimeTo)
                        .sportTypeId(sportTypeId)
                        .sportGroundId(sportGroundId)
                        .organizerId(organizerId)
                        .isPrivate(isPrivate)
                        .minPrice(minPrice)
                        .maxPrice(maxPrice)
                        .build(),
                        pageNumber
                )
        );
    }

    @ApiOperation(value = "Изменяет данные мероприятия (для организатора или Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping(value = "/{eventId}")
    public ResponseEntity<EventDto> updateEvent(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            @ApiParam(value = PAR_EVENT_DTO) @Valid @RequestBody EventRequest eventRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(eventService.update(eventRequest, eventId, authentication));
    }

    @ApiOperation(value = "Отображает данные мероприятия по его индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping(value = "/{eventId}")
    public ResponseEntity<EventDto> readEvent(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(eventService.getDtoById(eventId));
    }

    @ApiOperation(value = "Создает новое мероприятие (для зарегистрированного пользователя или Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("")
    public ResponseEntity<EventDto> createEvent(
            @ApiParam(value = PAR_EVENT_DTO) @Valid @RequestBody EventRequest eventRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(eventService.save(eventRequest, authentication));
    }

    @ApiOperation(value = "Удаляет мероприятие по его индексу (для организатора или Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            Authentication authentication
    ) {
        return eventService.deleteById(eventId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /*
     * Методы, работающие с участниками мероприятия
     */

    @ApiOperation(value = "Отображает список участников по идентификатору мероприятия")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<UserDto>> getAllParticipants(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(eventService.getParticipantsByEventId(eventId));
    }

    @ApiOperation(value = "Добавляет пользователя в список участников по идентификатору мероприятия и " +
            "данным авторизации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{eventId}/participants")
    public ResponseEntity<?> addParticipant(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            Authentication authentication
    ) {
        return eventService.addParticipantByAuthentication(eventId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Удаляет пользователя из списка участников по идентификаторам мероприятия и пользователя \n" +
            "(для организатора или Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{eventId}/participants/{userId}")
    public ResponseEntity<?> deleteParticipantById(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            @ApiParam(value = PAR_USER_ID) @PathVariable Long userId,
            Authentication authentication
    ) {
        return eventService.deleteParticipantByUserId(eventId, userId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Удаляет пользователя из списка участников мероприятия по данным авторизации \n" +
            "(для участника мероприятия)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{eventId}/participants")
    public ResponseEntity<?> deleteParticipantByAuthentication(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            Authentication authentication
    ) {
        return eventService.deleteParticipantByAuthentication(eventId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /*
     * Методы, работающие с приглашениями на мероприятия
     */

    @ApiOperation(value = "Отображает список приглашенных по идентификатору мероприятия")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{eventId}/invitations")
    public ResponseEntity<List<UserDto>> getAllInvitations(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(eventService.getAllInvitationsByEventId(eventId, authentication));
    }

    @ApiOperation(value = "Добавляет пользователя в список приглашенных по идентификаторам мероприятия и пользователя \n" +
            "(для организатора или Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{eventId}/invitations/{userId}")
    public ResponseEntity<?> addInvitation(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            @ApiParam(value = PAR_USER_ID) @PathVariable Long userId,
            Authentication authentication
    ) {
        return eventService.addInvitationByUserId(eventId, userId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Удаляет пользователя из списка приглашенных по идентификаторам мероприятия и пользователя \n" +
            "(для организатора или Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{eventId}/invitations/{userId}")
    public ResponseEntity<?> deleteInvitation(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            @ApiParam(value = PAR_USER_ID) @PathVariable Long userId,
            Authentication authentication
    ) {
        return eventService.deleteInvitationByUserId(eventId, userId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Принимает пользователем приглашение на участие в мероприятии. \n"
            + "Переносит пользователя из списка приглашенных в список участников мероприятия.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER})
    @PostMapping("/{eventId}/invitations")
    public ResponseEntity<?> acceptInvitation(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            Authentication authentication
    ) {
        return eventService.acceptInvitationByAuthentication(eventId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Отклоняет пользователем приглашение на участие в мероприятии. \n"
            + "Удаляет пользователя из списка приглашенных по идентификатору мероприятия и данным авторизации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER})
    @DeleteMapping("/{eventId}/invitations")
    public ResponseEntity<?> rejectInvitation(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            Authentication authentication
    ) {
        return eventService.rejectInvitationByAuthentication(eventId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /*
     * Методы, работающие с сообщениями мероприятий
     */

    @ApiOperation(value = "Отображает список сообщений мероприятия")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{eventId}/messages")
    public ResponseEntity<List<MessageDto>> readMessages(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(messageService.getAllDtoByEventId(eventId));
    }

    @ApiOperation(value = "Добавляет сообщение мероприятия")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{eventId}/messages")
    public ResponseEntity<MessageDto> createMessage(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            @ApiParam(value = PAR_MESSAGE_DTO) @Valid @RequestBody MessageRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(messageService.save(eventId, request, authentication));
    }

    @ApiOperation(value = "Обновляет данные сообщения")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping("/{eventId}/messages/{messageId}")
    public ResponseEntity<MessageDto> updateMessage(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            @ApiParam(value = PAR_MESSAGE_ID) @PathVariable Long messageId,
            @ApiParam(value = PAR_MESSAGE_DTO) @Valid @RequestBody MessageRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(messageService.update(eventId, messageId, request, authentication));
    }

    @ApiOperation(value = "Удаляет сообщение")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{eventId}/messages/{messageId}")
    public ResponseEntity<MessageDto> deleteMessage(
            @ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
            @ApiParam(value = PAR_MESSAGE_ID) @PathVariable Long messageId,
            Authentication authentication
    ) {
        return messageService.deleteById(eventId, messageId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
