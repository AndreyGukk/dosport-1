package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.services.api.SportGroundService;

import java.util.List;

/**
 * Контроллер Спортивных площадок.
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sportgrounds")
public class SportGroundController {

    private final SportGroundService sportGroundService;

    @ApiOperation(value = "Отображает данные всех площадок")
    @GetMapping
    public ResponseEntity<List<SportGroundDto>> readAllSportGrounds(@RequestParam(required = false) String city) {
        return ResponseEntity.ok(sportGroundService.getAllDto(city));
    }

    @ApiOperation(value = "Отображает данные площадки по её индексу")
    @GetMapping("/{id}")
    public ResponseEntity<SportGroundDto> readSportGround(@PathVariable Long id) {
        return ResponseEntity.ok(sportGroundService.getDtoById(id));
    }

    @ApiOperation(value = "Создаёт площадку")
    @PostMapping
    public ResponseEntity<?> createSportGround(@RequestBody SportGroundRequest groundRequest) {
        return ResponseEntity.ok(sportGroundService.create(groundRequest));
    }
}
