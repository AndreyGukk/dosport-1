package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.helpers.Roles;
import ru.dosport.services.api.SportGroundService;

import java.util.List;

import static ru.dosport.helpers.Roles.*;

/**
 * Контроллер Спортивных площадок.
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sportgrounds", produces = "application/json")
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

    @ApiOperation(value = "Удаляет площадку")
    @Secured(value = {ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSportGround(@PathVariable Long id, Authentication authentication) {
        return sportGroundService.delete(id, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет площадку")
    @PutMapping("/{id}")
    public ResponseEntity<SportGroundDto> updateSportGround(@PathVariable Long id, SportGroundDto sportGroundDto,
                                                            Authentication authentication) {
        return ResponseEntity.ok(sportGroundService.update(id, sportGroundDto, authentication));
    }
}
