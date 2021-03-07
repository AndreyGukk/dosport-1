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
import ru.dosport.dto.PasswordRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.services.api.UserService;

import javax.validation.Valid;

import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Api(value = "/api/v1/profile", tags = {"Контроллер профиля пользователя"})
public class UserController {

    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final UserService userService;

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "", produces = DATA_TYPE)
    @ApiOperation(value = "Отображает собственный профиль авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> readUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getDtoByAuthentication(authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PatchMapping(value = "", produces = DATA_TYPE, consumes = DATA_TYPE)
    @ApiOperation(value = "Изменяет собственный профиль авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              Authentication authentication) {
        return ResponseEntity.ok(userService.update(userDto, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "/{id}", produces = DATA_TYPE)
    @ApiOperation(value = "Возваращает пользователя по его id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> readUserById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(userService.getDtoById(id));
    }

    @PostMapping(value = "", produces = DATA_TYPE, consumes = DATA_TYPE)
    @ApiOperation(value = "Создает новый профиль пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.save(userRequest));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PatchMapping(value = "/password", produces = DATA_TYPE, consumes = DATA_TYPE)
    @ApiOperation(value = "Изменяет пароль авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    public ResponseEntity<?> updateUserPassword(@Valid @RequestBody PasswordRequest passwordRequest,
                                                Authentication authentication) {
        return userService.updatePassword(passwordRequest, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "/friends", produces = DATA_TYPE)
    @ApiOperation(value = "Отображает список друзей пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<UserDto>> readUserFriends(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserFriendsDtoByAuthentication(authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "/possibleFriends", produces = DATA_TYPE)
    @ApiOperation(value = "Отображает список пользователей, которые добавили пользователя в друзья")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<UserDto>> readPossibleUserFriends(Authentication authentication) {
        return ResponseEntity.ok(userService.getPossibleUserFriendsDtoByAuthentication(authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PatchMapping(value = "/friends/{friendId}", produces = DATA_TYPE, consumes = DATA_TYPE)
    @ApiOperation(value = "Добавляет в список друзей пользователя другого пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<?> addUserToFriends(@Valid @PathVariable Long friendId,
                                              Authentication authentication) {
        return ResponseEntity.ok(userService.addUserToFriendsByAuthentication(friendId, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping(value = "/friends/{friendId}", produces = DATA_TYPE, consumes = DATA_TYPE)
    @ApiOperation(value = "Удаляет из списка друзей пользователя другого пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<?> deleteUserFromFriends(@Valid @PathVariable Long friendId,
                                                   Authentication authentication) {
        return ResponseEntity.ok(userService.deleteUserFromFriendsByAuthentication(friendId, authentication));
    }
}
