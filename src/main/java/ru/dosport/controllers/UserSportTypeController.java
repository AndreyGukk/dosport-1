package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.dto.UserSportTypeDto;
import ru.dosport.services.api.UserSportTypeService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

/**
 * Контроллер Спортивных навыков пользователя.
 */
@ApiOperation("Контроллер спортивных навыков Пользователя")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/profile/sporttype")
@RequiredArgsConstructor
public class UserSportTypeController {

    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final UserSportTypeService userSportTypeService;

    @ApiOperation(value = "Выводит список навыков пользователся по id пользователя")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/{id}")
    public ResponseEntity<List<UserSportTypeDto>> readAllSportTypesByUserId(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userSportTypeService.getAllDtoByUserId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Выводит список навыков пользователя, для которых не установлен уровень")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/unused")
    public ResponseEntity<List<SportTypeDto>> readEmptySportTypesByUser(Authentication authentication) {
        return new ResponseEntity<>(userSportTypeService.getEmptyDtoByUser(authentication), HttpStatus.OK);
    }

    @ApiOperation(value = "Выводит список собственных навыков пользователся")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("")
    public ResponseEntity<List<UserSportTypeDto>> readAllSportTypes(Authentication authentication) {
        return new ResponseEntity<>(userSportTypeService.getAllDtoByUserAuthentication(authentication), HttpStatus.OK);
    }

    @ApiOperation(value = "Изменяет список навыков пользователся")
    @Secured(value = {ROLE_USER})
    @PutMapping("")
    public ResponseEntity<List<UserSportTypeDto>> updateUserSportTypesByUserId(@Valid List<UserSportTypeDto> dtoList,
                                                                               Authentication authentication) {
        return new ResponseEntity<>(userSportTypeService.update(dtoList, authentication), HttpStatus.OK);
    }
}
