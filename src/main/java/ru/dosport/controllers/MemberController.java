package ru.dosport.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.dto.EventDto;
import ru.dosport.dto.MemberDto;
import ru.dosport.dto.MemberRequest;
import ru.dosport.services.api.EventMemberService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/events", produces = "application/json")
@Api(value = "/api/v1/events", tags = {"Контроллер Участников"})
public class MemberController {

    // Список необходимых зависимостей
    private final EventMemberService memberService;

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
}
