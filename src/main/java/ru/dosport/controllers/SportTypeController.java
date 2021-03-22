package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.dto.SportTypeDto;
import ru.dosport.services.api.SportTypeService;

import java.util.List;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.SwaggerMessages.PAR_SPORT_TYPE_ID;
import static ru.dosport.helpers.SwaggerMessages.PAR_SPORT_TYPE_NAME;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sporttype", produces = "application/json")
@Api(value = "/api/v1/profile", tags = {"Контроллер Видов спорта"}, produces = "application/json")
public class SportTypeController {

    // Необходимые сервисы
    private final SportTypeService sportTypeService;

    @ApiOperation(value = "Отображает список всех видов спорта")
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
    @GetMapping("/{sportTypeId}")
    public ResponseEntity<SportTypeDto> readSportType(
            @ApiParam(PAR_SPORT_TYPE_ID) @PathVariable Short sportTypeId
    ) {
        return ResponseEntity.ok(sportTypeService.getSportTypeDtoById(sportTypeId));
    }

    @ApiOperation(value = "Создаёт вид спорта (только для Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @PostMapping
    public ResponseEntity<SportTypeDto> createSportType(
            @ApiParam(PAR_SPORT_TYPE_NAME) @RequestBody String sportTitle
    ) {
        return ResponseEntity.ok(sportTypeService.addSportByAuthentication(sportTitle));
    }

    @ApiOperation(value = "Удаляет вид спорта (только для Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @DeleteMapping("/{sportTypeId}")
    public ResponseEntity<?> deleteSportType(
            @ApiParam(PAR_SPORT_TYPE_ID) @PathVariable Short sportTypeId
    ) {
        return sportTypeService.deleteById(sportTypeId) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет вид спорта (только для Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @PutMapping("/{sportTypeId}")
    public ResponseEntity<SportTypeDto> updateSportType(
            @ApiParam(PAR_SPORT_TYPE_ID) @PathVariable Short sportTypeId,
            @ApiParam(PAR_SPORT_TYPE_ID) @RequestBody String sportTittle
    ) {
        return ResponseEntity.ok(sportTypeService.update(sportTypeId, sportTittle));
    }
}
