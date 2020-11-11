package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.helpers.Roles;
import ru.dosport.services.api.SportTypeService;

import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;

/**
 * Контроллер Видов спорта.
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sporttype")
public class SportTypeController {

    // Необходимые сервисы
    private final SportTypeService typeService;

    @ApiOperation(value = "Отображает данные видов спорта")
    @GetMapping
    public ResponseEntity<List<SportTypeDto>> readAllSportTypes() {
        return ResponseEntity.ok(typeService.getAllSportTypeDto());
    }

    @ApiOperation(value = "Отображает данные вида спорта по его индексу")
    @GetMapping("/{id}")
    public ResponseEntity<SportTypeDto> readSportType(@PathVariable Short id) {
        return ResponseEntity.ok(typeService.getSportTypeDtoById(id));
    }

    @ApiOperation(value = "Создаёт вид спорта")
    @PostMapping
    public ResponseEntity<?> createSportType(@RequestBody String sportTitle) {
        return typeService.save(sportTitle)  != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Удаляет вид спорта")
    @Secured(value = {ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSportType(@PathVariable Short id) {
        return typeService.deleteById(id) ?
                ResponseEntity.badRequest().build() : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Обновляет вид спорта")
    @Secured(value = {ROLE_ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<SportTypeDto> updateSportType(@PathVariable Short id, @RequestBody String tittle) {
        return ResponseEntity.ok(typeService.update(id, tittle));
    }

}
