package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.SportGroundRequest;
import ru.dosport.dto.UserSportGroundDto;
import ru.dosport.services.api.SportGroundService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Messages.*;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @GetMapping
    public ResponseEntity<List<SportGroundDto>> readAllSportGrounds(@RequestParam(required = false) String city) {
        return ResponseEntity.ok(sportGroundService.getAllDto(city));
    }

    @ApiOperation(value = "Отображает данные площадки по её индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SportGroundDto> readSportGround(@PathVariable Long id) {
        return ResponseEntity.ok(sportGroundService.getDtoById(id));
    }

    @ApiOperation(value = "Создаёт площадку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @PostMapping
    public ResponseEntity<?> createSportGround(@Valid @RequestBody SportGroundRequest groundRequest) {
        return ResponseEntity.ok(sportGroundService.create(groundRequest));
    }

    @ApiOperation(value = "Удаляет площадку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @Secured(value = {ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSportGround(@PathVariable Long id, Authentication authentication) {
        return sportGroundService.delete(id, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет площадку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @Secured(value = {ROLE_ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<SportGroundDto> updateSportGround(@PathVariable Long id,
                                                            @Valid @RequestBody SportGroundRequest request,
                                                            Authentication authentication) {
        return ResponseEntity.ok(sportGroundService.update(id, request, authentication));
    }

    @ApiOperation("Выводит список избранных площадок пользователя")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping ("/my")
    public ResponseEntity<List<SportGroundDto>> readAllSportGroundsByAuth (Authentication authentication) {
        return ResponseEntity.ok(sportGroundService.getAllDtoByAuth(authentication));
    }

    @ApiOperation("Добавляет площадку в список избранных")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @PostMapping ("/my")
    public ResponseEntity<UserSportGroundDto> createUserSportGroundsByAuth (@RequestBody SportGroundDto sportGroundDto,
                                                                            Authentication authentication) {
        return ResponseEntity.ok(sportGroundService.saveUserSportGroundDtoByAuth(authentication, sportGroundDto));
    }

    @ApiOperation("Удаляет площадку из списка избранных по индексу площадки")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @DeleteMapping("/my/{id}")
    public ResponseEntity<?> deleteBySportGroundId (@PathVariable Long id,
                                                    Authentication authentication) {
        return sportGroundService.deleteFavoritesBySportGroundId(id, authentication)?
                ResponseEntity.badRequest().build() : ResponseEntity.noContent().build();
    }
}
