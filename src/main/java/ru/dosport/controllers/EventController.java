package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.EventRequest;
import ru.dosport.dto.UserEventDto;
import ru.dosport.services.api.EventService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static ru.dosport.helpers.MessageSwagger.PAR_EVENT_ID;
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

    @ApiOperation(value = "Отображает данные всех мероприятий")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping
    public ResponseEntity<List<EventDto>> readAllEvent() {
        return ResponseEntity.ok(eventService.getAllDto());
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

    @ApiOperation(value = "Отображает список мероприятий пользователя за период с ___ на ___ дней (1/7/31")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping ("/calendar/interval")
    public ResponseEntity<List<UserEventDto>> readAllEventByAuth(Authentication authentication,
                                                                 @RequestParam  LocalDate from,
                                                                 @RequestParam  byte timeInterval){
        return ResponseEntity.ok(eventService.getAllDtoByAuthTimeInterval(authentication, from, timeInterval));
    }

    @ApiOperation(value = "Отображает список мероприятий пользователя за период с ___ по ___")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping ("/calendar")
    public ResponseEntity<List<UserEventDto>> readAllEventByAuth(Authentication authentication,
                                                                 @RequestParam  LocalDate from,
                                                                 @RequestParam  LocalDate to){
        return ResponseEntity.ok(eventService.getAllDtoByAuthFromTo(authentication, from, to));
    }
}
