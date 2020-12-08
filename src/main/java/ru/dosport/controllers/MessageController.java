package ru.dosport.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.EventMessageDto;
import ru.dosport.dto.EventMessageRequest;

import ru.dosport.services.api.EventMessageService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.MessageSwagger.PAR_EVENT_ID;
import static ru.dosport.helpers.MessageSwagger.PAR_MESSAGE_ID;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/events/{eventId}", produces = "application/json")
@Api(value = "/api/v1/events", tags = {"Контроллер Сообщений"})
public class MessageController {

    private final EventMessageService eventMessageService;

    @ApiOperation(value = "Отоброжает данные всех сообщений мероприятия")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/messages")
    public ResponseEntity<List<EventMessageDto>> readBoard(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId) {
        return ResponseEntity.ok(eventMessageService.getAllDtoByEventId(eventId));
    }

    @ApiOperation(value = "Добавляет сообщение к мероприятию")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/messages")
    public ResponseEntity<EventMessageDto> createMessage(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
                                                         @Valid @RequestBody EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.save(eventId, request, authentication));
    }

    @ApiOperation(value = "Обновляет данные сообщения")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping("/messages/{messageId}")
    public ResponseEntity<EventMessageDto> updateMessage(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
                                                         @ApiParam(value = PAR_MESSAGE_ID) @PathVariable Long messageId,
                                                         @Valid @RequestBody EventMessageRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.ok(eventMessageService.update(messageId, eventId, request, authentication));
    }

    @ApiOperation(value = "Удаляет сообщение")
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<EventMessageDto> deleteMessage(@ApiParam(value = PAR_EVENT_ID) @PathVariable Long eventId,
                                                         @ApiParam(value = PAR_MESSAGE_ID) @PathVariable Long messageId,
                                                         Authentication authentication) {
        return eventMessageService.deleteById(messageId, eventId, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
