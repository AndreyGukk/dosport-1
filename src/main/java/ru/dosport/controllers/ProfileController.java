package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.dto.PasswordRequest;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.dto.UserDto;
import ru.dosport.services.api.SportTypeService;
import ru.dosport.services.api.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "/api/v1/profiles", tags = {"Контроллер Профилей пользователей"}, produces = "application/json")
public class ProfileController {

    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final UserService userService;
    private final SportTypeService sportTypeService;

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "")
    @ApiOperation(value = "Отображает собственный профиль авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> readProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getDtoByAuthentication(authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping(value = "", consumes = DATA_TYPE)
    @ApiOperation(value = "Изменяет собственный профиль авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> updateProfile(
            @ApiParam("Измененные данные пользователя") @Valid @RequestBody UserDto userDto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(userService.update(userDto, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "/{userId}")

    @ApiOperation(value = "Отображает профиль пользователя по его userId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<UserDto> readProfileById(
            @ApiParam("Идентификатор пользователя") @Valid @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.getDtoById(userId));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping(value = "/password", consumes = DATA_TYPE)
    @ApiOperation(value = "Изменяет пароль авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    public ResponseEntity<?> updatePassword(
            @ApiParam("Запрос на изменение пароля") @Valid @RequestBody PasswordRequest passwordRequest,
            Authentication authentication
    ) {
        return userService.updatePassword(passwordRequest, authentication) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping(value = "", consumes = DATA_TYPE)
    @ApiOperation(value = "Полность удаляет профиль и все данные авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    public ResponseEntity<?> deleteProfile(Authentication authentication) {
        return userService.deleteByAuthentication(authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    /*
     * Методы, относящиеся к предпочитаемым видам спорта пользователя
     */

    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/sports")
    @ApiOperation(value = "Выводит список предпочитаемых видов спорта пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    public ResponseEntity<List<SportTypeDto>> readSports(Authentication authentication) {
        return ResponseEntity.ok(sportTypeService.getAllSportDtoByAuthentication(authentication));
    }

    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @PutMapping("/sports")
    @ApiOperation(value = "Изменяет список предпочитаемых видов спорта пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    public ResponseEntity<List<SportTypeDto>> updateSports(
            @ApiParam("Данные вида спарта") @Valid @RequestBody List<SportTypeDto> dtoList,
            Authentication authentication
    ) {
        return ResponseEntity.ok(sportTypeService.updateSportsByAuthentication(dtoList, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping(value = "/sports/{sportTypeId}")
    @ApiOperation(value = "Добавляет спорт в список предпочитаемых видов спорта пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<SportTypeDto>> addSport(
            @ApiParam("Идентификатор вида спорта") @Valid @PathVariable Short sportTypeId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(sportTypeService.addSportByAuthentication(sportTypeId, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping(value = "/sports/{sportTypeId}")
    @ApiOperation(value = "Удаляет спорт из списка предпочитаемых видов спорта пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<SportTypeDto>> deleteSport(
            @ApiParam("Идентификатор вида спорта") @Valid @PathVariable Short sportTypeId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(sportTypeService.deleteSportByAuthentication(sportTypeId, authentication));
    }

    /*
     * Методы относящиеся к подпискам и подписчикам пользователя
     */

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "/subscribers")
    @ApiOperation(value = "Отображает подписчиков - список пользователей, которые подписаны на данного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<UserDto>> readSubscribers(Authentication authentication) {
        return ResponseEntity.ok(userService.getSubscribersByAuthentication(authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping(value = "/subscriptions")
    @ApiOperation(value = "Отображает подписки - список пользователей, на которых подписался данный пользователь")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<UserDto>> readSubscriptions(Authentication authentication) {
        return ResponseEntity.ok(userService.getSubscriptionsByAuthentication(authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping(value = "/subscriptions/{userId}")
    @ApiOperation(value = "Добавляет в подписки пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<UserDto>> addSubscription(
            @ApiParam("Идентификатор пользователя") @Valid @PathVariable Long userId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(userService.addSubscriptionByAuthentication(userId, authentication));
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping(value = "/subscriptions/{userId}")
    @ApiOperation(value = "Удаляет из подписок пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST, response = boolean.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    public ResponseEntity<List<UserDto>> deleteSubscription(
            @ApiParam("Идентификатор пользователя") @Valid @PathVariable Long userId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(userService.deleteSubscriptionByAuthentication(userId, authentication));
    }
}
