package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.services.core.SportTypeService;

import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;

/**
 * Контроллер списка навыков Пользователя
 */
@ApiOperation("Контроллер видов спорта")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/sporttype")
@RequiredArgsConstructor
public class SportTypeController {
    //Список необходимых сервисов
    private final SportTypeService sportTypeService;

    @ApiOperation(value = "Выводит список видов спорта")
    @Secured(value = ROLE_ADMIN)
    @GetMapping
    public ResponseEntity<List<SportTypeDto>> readAllSportTypes() {
        List<SportTypeDto> sportTypeDtosList = sportTypeService.getAllDto();
        return sportTypeDtosList.size() == 0 ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(sportTypeDtosList, HttpStatus.OK);
    }

    @ApiOperation("Добавляет вид спорта")
    @Secured(value = ROLE_ADMIN)
    @PostMapping("/add")
    public ResponseEntity<SportTypeDto> createSportType(
                                                        @RequestBody SportTypeDto sportTypeDto) {
        return new ResponseEntity(sportTypeService.save(sportTypeDto), HttpStatus.OK);
    }

    @ApiOperation("Удаляет вид спорта")
    @Secured(value = ROLE_ADMIN)
    @PostMapping("/{id}")
    public ResponseEntity<Boolean> deleteSportTypeById(@PathVariable("id") long id) {
        return new ResponseEntity(sportTypeService.deleteById(id), HttpStatus.OK);
    }
}
