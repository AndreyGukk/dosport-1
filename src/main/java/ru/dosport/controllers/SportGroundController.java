package ru.dosport.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.services.api.SportGroundService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sportgrounds")
public class SportGroundController {

    private final SportGroundService sportGroundService;

    @GetMapping
    public ResponseEntity<List<SportGroundDto>> readAllSportGround() {
        return ResponseEntity.ok(sportGroundService.getAllDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportGroundDto> readSportGround(@PathVariable Long id) {
        return ResponseEntity.ok(sportGroundService.getSportGroundDtoById(id));
    }

    @PostMapping
    public ResponseEntity<?> createSportGround(@RequestBody SportGroundRequest groundRequest) {
        return ResponseEntity.ok(sportGroundService.create(groundRequest));
    }
}
