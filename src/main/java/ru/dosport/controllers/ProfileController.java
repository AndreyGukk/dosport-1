package ru.dosport.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.*;
import ru.dosport.services.api.UserService;
import ru.dosport.services.api.UserSportTypeService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Api(value = "/api/v1/profile", tags = {"Контроллер Профиля пользователя"})
public class ProfileController {

    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final UserService userService;
    private final UserSportTypeService userSportTypeService;

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

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PatchMapping(value = "/password", produces = DATA_TYPE, consumes = DATA_TYPE)
    @ApiOperation(value = "Изменяет пароль авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
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
    @GetMapping(value = "/relations", produces = DATA_TYPE)
    @ApiOperation(value = "Отображает список пользователей, которые добавили пользователя в друзья")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<UserDto>> readRelatedUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getRelatedUsersDtoByAuthentication(authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PatchMapping(value = "/friends/{friendId}", produces = DATA_TYPE)
    @ApiOperation(value = "Добавляет в список друзей пользователя другого пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<?> addUserToFriends(@Valid @PathVariable Long friendId,
                                              Authentication authentication) {
        return ResponseEntity.ok(userService.addUserToFriendsByAuthentication(friendId, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping(value = "/friends/{friendId}", produces = DATA_TYPE)
    @ApiOperation(value = "Удаляет из списка друзей пользователя другого пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<?> deleteUserFromFriends(@Valid @PathVariable Long friendId,
                                                   Authentication authentication) {
        return ResponseEntity.ok(userService.deleteUserFromFriendsByAuthentication(friendId, authentication));
    }

    /**
     * Методы, относящиеся к видам спорта (навыкам) пользователя
     */

    @ApiOperation(value = "Выводит список навыков пользователся по id пользователя")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/sporttype/{id}")
    public ResponseEntity<List<UserSportTypeDto>> readAllSportTypesByUserId(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userSportTypeService.getAllDtoByUserId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Выводит список навыков пользователя, для которых не установлен уровень")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/sporttype/unused")
    public ResponseEntity<List<SportTypeDto>> readEmptySportTypesByUser(Authentication authentication) {
        return new ResponseEntity<>(userSportTypeService.getEmptyDtoByUser(authentication), HttpStatus.OK);
    }

    @ApiOperation(value = "Выводит список собственных навыков пользователся")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/sporttype")
    public ResponseEntity<List<UserSportTypeDto>> readAllSportTypes(Authentication authentication) {
        return new ResponseEntity<>(userSportTypeService.getAllDtoByUserAuthentication(authentication), HttpStatus.OK);
    }

    @ApiOperation(value = "Изменяет список навыков пользователся")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER})
    @PutMapping("/sporttype")
    public ResponseEntity<List<UserSportTypeDto>> updateUserSportTypesByUserId(@Valid List<UserSportTypeDto> dtoList,
                                                                               Authentication authentication) {
        return new ResponseEntity<>(userSportTypeService.update(dtoList, authentication), HttpStatus.OK);
    }
}
