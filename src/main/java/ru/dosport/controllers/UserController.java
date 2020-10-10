package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.PasswordRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.services.api.UserService;

import static ru.dosport.entities.Roles.ROLE_ADMIN;
import static ru.dosport.entities.Roles.ROLE_USER;
import static ru.dosport.mappers.ResponseMapper.getBooleanResponse;
import static ru.dosport.mappers.ResponseMapper.getDtoResponse;

/**
 * Контроллер профиля пользователя.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UserController {

    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final UserService userService;

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Выводит данные пользователя")
    @GetMapping(value = "", produces = DATA_TYPE)
    public ResponseEntity<UserDto> readUser(Authentication authentication) {
        return getDtoResponse(userService.getByUsername(authentication.getName()));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Изменяет данные пользователя")
    @PostMapping(value = "", produces = DATA_TYPE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              Authentication authentication) {
        return getDtoResponse(userService.update(userDto, authentication.getName()));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @ApiOperation(value = "Изменяет пароль пользователя")
    @PostMapping(value = "/password", produces = DATA_TYPE)
    public ResponseEntity<Boolean> updateUserPassword(@RequestBody PasswordRequest passwordRequest,
                                                      Authentication authentication) {
        return getBooleanResponse(userService.updatePassword(passwordRequest, authentication));
    }

    @ApiOperation(value = "Создает новый профиль пользователя")
    @PostMapping(value = "/create", produces = DATA_TYPE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest userRequest) {
        return getDtoResponse(userService.save(userRequest));
    }
}
