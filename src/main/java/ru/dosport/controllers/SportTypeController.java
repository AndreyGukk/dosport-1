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
import ru.dosport.dto.SportTypeDto;
import ru.dosport.services.api.SportTypeService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sporttype", produces = "application/json")
@Api(value = "/api/v1/profile", tags = {"Контроллер Видов спорта"})
public class SportTypeController {

    // Необходимые сервисы
    private final SportTypeService sportTypeService;

    @ApiOperation(value = "Отображает данные видов спорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST)
    })
    @GetMapping
    public ResponseEntity<List<SportTypeDto>> readAllSportTypes() {
        return ResponseEntity.ok(sportTypeService.getAllSportTypeDto());
    }

    @ApiOperation(value = "Отображает данные вида спорта по его индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SportTypeDto> readSportType(@PathVariable Short id) {
        return ResponseEntity.ok(sportTypeService.getSportTypeDtoById(id));
    }

    @ApiOperation(value = "Создаёт вид спорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @PostMapping
    public ResponseEntity<?> createSportType(@RequestBody String sportTitle) {
        return sportTypeService.save(sportTitle)  != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Удаляет вид спорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSportType(@PathVariable Short id) {
        return sportTypeService.deleteById(id) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет вид спорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<SportTypeDto> updateSportType(@PathVariable Short id, @RequestBody String tittle) {
        return ResponseEntity.ok(sportTypeService.update(id, tittle));
    }

    /*
     * Методы, относящиеся к предпочитаемым видам спорта пользователя
     */

    @ApiOperation(value = "Выводит список предпочитаемых видов спорта пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/sports")
    public ResponseEntity<List<SportTypeDto>> readSports(Authentication authentication) {
        return ResponseEntity.ok(sportTypeService.getAllDtoByUserAuthentication(authentication));
    }

    @ApiOperation(value = "Выводит список предпочитаемых видов спорта по id пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/sports/{id}")
    public ResponseEntity<List<SportTypeDto>> readSportsByUserId(@PathVariable("userId") long id) {
        return ResponseEntity.ok(sportTypeService.getAllDtoByUserId(id));
    }

    @ApiOperation(value = "Изменяет список предпочитаемых видов спорта пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER})
    @PatchMapping("/sports")
    public ResponseEntity<List<SportTypeDto>> updateSportsByUserId(@Valid List<SportTypeDto> dtoList,
                                                                   Authentication authentication) {
        return ResponseEntity.ok(sportTypeService.update(dtoList, authentication));
    }
}
