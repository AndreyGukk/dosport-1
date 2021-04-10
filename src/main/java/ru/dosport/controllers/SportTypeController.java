package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.services.api.SportTypeService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
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
    public ResponseEntity<Set<String>> readAllSportTypes() {
        return ResponseEntity.ok(sportTypeService.getAllSportType());
    }

    @ApiOperation(value = "Создаёт вид спорта (только для Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @PostMapping
    public ResponseEntity<?> createSportType(
            @ApiParam(PAR_SPORT_TYPE_NAME) @Valid @NotBlank(message = DATA_NOT_BLANK) @RequestBody String sportTitle
    ) {
        return ResponseEntity.ok(sportTypeService.addSportType(sportTitle));
    }

    @ApiOperation(value = "Удаляет вид спорта (только для Администратора)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 403, message = ACCESS_DENIED, response = ErrorDto.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @DeleteMapping
    public ResponseEntity<?> deleteSportType(
            @ApiParam(PAR_SPORT_TYPE_NAME) @Valid @RequestBody String sportTypeTitle
    ) {
        return sportTypeService.deleteByTitle(sportTypeTitle) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }
}
