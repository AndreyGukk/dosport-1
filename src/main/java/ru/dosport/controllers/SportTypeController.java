package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.services.api.SportTypeService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sporttype")
public class SportTypeController {

    private final SportTypeService typeService;

    @ApiOperation(value = "Отображает данные видов спорта")
    @GetMapping
    public ResponseEntity<List<SportTypeDto>> readAllSportType() {
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
}
