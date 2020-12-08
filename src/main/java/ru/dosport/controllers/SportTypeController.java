package ru.dosport.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.services.api.SportTypeService;

import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sporttype", produces = "application/json")
@Api(value = "/api/v1/profile", tags = {"Контроллер Видов спорта"})
public class SportTypeController {

    // Необходимые сервисы
    private final SportTypeService typeService;

    @ApiOperation(value = "Отображает данные видов спорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST)
    })
    @GetMapping
    public ResponseEntity<List<SportTypeDto>> readAllSportTypes() {
        return ResponseEntity.ok(typeService.getAllSportTypeDto());
    }

    @ApiOperation(value = "Отображает данные вида спорта по его индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SportTypeDto> readSportType(@PathVariable Short id) {
        return ResponseEntity.ok(typeService.getSportTypeDtoById(id));
    }

    @ApiOperation(value = "Создаёт вид спорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @PostMapping
    public ResponseEntity<?> createSportType(@RequestBody String sportTitle) {
        return typeService.save(sportTitle)  != null ?
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
        return typeService.deleteById(id) ?
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
        return ResponseEntity.ok(typeService.update(id, tittle));
    }
}
