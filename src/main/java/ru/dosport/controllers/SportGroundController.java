package ru.dosport.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.*;
import ru.dosport.services.api.ReviewService;
import ru.dosport.services.api.SportGroundService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sportgrounds", produces = "application/json")
@Api(tags = {"Контроллер Спортивных площадок"})
public class SportGroundController {

    // Список необходимых зависимостей
    private final SportGroundService sportGroundService;
    private final ReviewService reviewService;

    @ApiOperation(value = "Отображает данные всех площадок по определенному городу и виду спорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping
    public ResponseEntity<List<SportGroundDto>> readAllSportGroundsByCityAndSportType(
            @RequestParam(required = false) String city, @RequestParam (required = false) Short sportTypeId) {
        return ResponseEntity.ok(sportGroundService.getAllDtoByCityAndSportTypeId(city, sportTypeId));
    }

    @ApiOperation(value = "Отображает данные площадки по её индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SportGroundDto> readSportGroundById(@PathVariable Long id) {
        return ResponseEntity.ok(sportGroundService.getDtoById(id));
    }

    @ApiOperation(value = "Создаёт площадку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @PostMapping
    public ResponseEntity<?> createSportGround(@Valid @RequestBody SportGroundRequest groundRequest) {
        return ResponseEntity.ok(sportGroundService.create(groundRequest));
    }

    @ApiOperation(value = "Удаляет площадку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSportGroundById(@PathVariable Long id,
                                                   Authentication authentication) {
        return sportGroundService.delete(id, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет площадку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
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

    /**
     * Методы работы с Отзывами пользователя о спортивных площадках
     */

    @ApiOperation(value = "Отображает отзыв по индентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/{sportGroundsId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long sportGroundsId,
                                               @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.readReviewDtoById(reviewId, sportGroundsId));
    }

    @ApiOperation(value = "Отображает отзывы спортивной площадки")
    @ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST)})
    @GetMapping("/{sportGroundsId}/reviews")
    public ResponseEntity<List<ReviewDto>> getAllReviews(@PathVariable Long sportGroundsId) {
        return ResponseEntity.ok(reviewService.readAllReviewsDtoBySportGround(sportGroundsId));
    }

    @ApiOperation(value = "Создает новый отзыв пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{sportGroundsId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable Long sportGroundsId,
                                                  @Valid @RequestBody ReviewRequest request,
                                                  Authentication authentication) {
        return ResponseEntity.ok(reviewService.saveReview(sportGroundsId, request, authentication));
    }

    @ApiOperation(value = "Редактирует отзыв пользователя. Редактировать может только сам пользователь")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping("/{sportGroundsId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long sportGroundsId,
                                                  @PathVariable Long reviewId,
                                                  @Valid @RequestBody ReviewRequest request,
                                                  Authentication authentication) {
        return ResponseEntity.ok(reviewService.updateReview(sportGroundsId, reviewId, request, authentication));
    }

    @ApiOperation(value = "Удаляет отзыв пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{sportGroundsId}/review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long sportGroundsId,
                                          @PathVariable Long reviewId,
                                          Authentication authentication) {
        return reviewService.deleteById(reviewId, sportGroundsId, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }
}
