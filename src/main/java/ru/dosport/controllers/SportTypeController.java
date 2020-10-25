package ru.dosport.controllers;

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

    @GetMapping
    public ResponseEntity<List<SportTypeDto>> readAllSportType() {
        return ResponseEntity.ok(typeService.getAllSportTypeDto());
    }

    @PostMapping
    public ResponseEntity<?> createSportType(@RequestBody String sportTitle) {
        return typeService.save(sportTitle)  != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
