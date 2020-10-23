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

import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

/**
 * Контроллер списка навыков Пользователя
 */
@ApiOperation("Контроллер навыков пользователя")
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
    public ResponseEntity<List<UserSportTypeDto>> readAllSportTypesByUserId(@PathVariable("userId") long id,
                                                                            Authentication authentication) {
        List<UserSportTypeDto> userSportTypeDtoList = userSportTypeService.getAllDtoByUserId(id, authentication);
        return userSportTypeDtoList.size() == 0 ?
                new ResponseEntity<>(userSportTypeService.createUserSportTypesList(id, authentication), HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(userSportTypeDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Изменяет список навыков пользователся по id пользователя")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @PostMapping("/{id}")
    public ResponseEntity<List<UserSportTypeDto>> updateUserSportTypesByUserId(@PathVariable("userId") long id,
                                                                               List<UserSportTypeDto> dtoList,
                                                                       Authentication authentication) {
        List<UserSportTypeDto> sportTypeDtosList = userSportTypeService.updateByUserId(id, dtoList, authentication);
        return sportTypeDtosList.size() == 0 ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(sportTypeDtosList, HttpStatus.OK);
    }
}
