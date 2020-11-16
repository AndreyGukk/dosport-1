package ru.dosport.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.SportGroundDto;
import ru.dosport.dto.UserSportGroundDto;
import ru.dosport.entities.SportGround;
import ru.dosport.services.api.UserSportGroundService;
import java.util.List;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@ApiOperation("Контроллер списка избранных площадок пользователя")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/profile/sportgrounds")
@RequiredArgsConstructor
public class UserSportGroundController {
    // Тип данных
    private final String DATA_TYPE = "application/json";

    // Необходимые сервисы
    private final UserSportGroundService userSportGroundService;

    @ApiOperation("Выводит список избранных площадок пользователя")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping
    public ResponseEntity<List<UserSportGroundDto>> readAllSportGroundsByAuth (Authentication authentication) {
        return ResponseEntity.ok(userSportGroundService.getAllDtoByAuth(authentication));
    }

    @ApiOperation("Добавляет площадку в список избранных")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @PostMapping
    public ResponseEntity<UserSportGroundDto> createUserSportGroundsByAuth (@RequestBody SportGroundDto sportGroundDto,
                                                                            Authentication authentication) {
        return ResponseEntity.ok(userSportGroundService.addDtoByAuth(authentication, sportGroundDto));
    }

    @ApiOperation("Удаляет площадку из списка избранных по индексу площадки")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBySportGroundId (@PathVariable Long id,
                                                               Authentication authentication) {
        return userSportGroundService.deleteBySportGroundId(id, authentication)?
                ResponseEntity.badRequest().build() : ResponseEntity.noContent().build();
    }
}
