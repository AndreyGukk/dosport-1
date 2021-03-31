package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.*;
import ru.dosport.services.api.ReviewService;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.specifications.SportGroundSearchCriteria;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static ru.dosport.helpers.InformationMessages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;
import static ru.dosport.helpers.SwaggerMessages.*;

/**
 * 1.Добавить свойства:
 * 3.1) Карта площадок. GET. способ получение площадок по координатам в определенном диапозоне
 * <p>
 * 2) Список площадок. GET. сПособ получение площадок без порядка в кол-ве 15-20 штук. В случае если указаны фильтры у фронта,
 * то из спарсенных 15-20 показать что совпало с фильтром, и если это числи меньше 15, то запросить повторно 15-20. Если спаршено 15 или больше,
 * ждать вызов пагинации
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sportgrounds", produces = "application/json")
@Api(tags = {"Контроллер Спортивных площадок"})
public class SportGroundController {

    // Список необходимых зависимостей
    private final SportGroundService sportGroundService;
    private final ReviewService reviewService;

    @ApiOperation(value = "Создаёт площадку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @PostMapping
    public ResponseEntity<?> createSportGround(
            @ApiParam(value = PAR_SPORTGROUND_DTO) @Valid @RequestBody SportGroundRequest groundRequest) {
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
    public ResponseEntity<?> deleteSportGroundById(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long id,
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
    public ResponseEntity<SportGroundDto> updateSportGround(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long id,
            @ApiParam(value = PAR_SPORTGROUND_DTO) @Valid @RequestBody SportGroundRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(sportGroundService.update(id, request, authentication));
    }

    /*
     * Методы получения площадки под запрос с фильтрами
     */

    @ApiOperation(value = "Отображает данные площадки по её индексу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SportGroundDto> readSportGroundById(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long id) {
        return ResponseEntity.ok(sportGroundService.getDtoById(id));
    }


    @ApiOperation(value = "Отображает данные всех площадок по фильтру")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("")
    public ResponseEntity<List<SportGroundDto>> readAllSportGroundsBySearchCriteria(
            @ApiParam(value = PAR_SPORTGROUND_CITY) @RequestParam(required = false) String city,
            @ApiParam(value = PAR_SPORTGROUND_ADDRESS) @RequestParam(required = false) String address,
            @ApiParam(value = PAR_SPORTGROUND_METRO) @RequestParam(required = false) String metroStation,
            @ApiParam(value = PAR_SPORTGROUND_SURFACE) @RequestParam(required = false) String surfaceType,
            @ApiParam(value = PAR_SPORTGROUND_LATITUDE_MIN) @RequestParam(required = false) Double minLatitude,
            @ApiParam(value = PAR_SPORTGROUND_LATITUDE_MAX) @RequestParam(required = false) Double maxLatitude,
            @ApiParam(value = PAR_SPORTGROUND_LONGITUDE_MIN) @RequestParam(required = false) Double minLongitude,
            @ApiParam(value = PAR_SPORTGROUND_LONGITUDE_MAX) @RequestParam(required = false) Double maxLongitude,
            @ApiParam(value = PAR_PRICE_MIN) @RequestParam(required = false) Integer minRentPrice,
            @ApiParam(value = PAR_PRICE_MAX) @RequestParam(required = false) Integer maxRentPrice,
            @ApiParam(value = PAR_SPORTGROUND_OPENED) @RequestParam(required = false) Boolean opened,
            @ApiParam(value = PAR_SPORTGROUND_INFRASTRUCTURES) @RequestParam(required = false) Set<String> infrastructures,
            @ApiParam(value = PAR_SPORT_TYPE_LIST) @RequestParam(required = false) Set<String> sportTypes,
            @ApiParam(value = PAR_PAGE_NUMBER, defaultValue = "0") @RequestParam(required = false, defaultValue = "0") Integer pageNumber

    ) {
        return ResponseEntity.ok(sportGroundService.
                getAllDtoBySearchCriteria(
                        SportGroundSearchCriteria.builder()
                                .city(city)
                                .address(address)
                                .metroStation(metroStation)
                                .surfaceType(surfaceType)
                                .minLatitude(minLatitude)
                                .maxLatitude(maxLatitude)
                                .minLongitude(minLongitude)
                                .maxLongitude(maxLongitude)
                                .minRentPrice(minRentPrice)
                                .maxRentPrice(maxRentPrice)
                                .opened(opened)
                                .infrastructures(infrastructures)
                                .sportTypes(sportTypes)
                                .build(),
                        pageNumber
                ));
    }

    /*
     * Методы работы с Избранными площадками пользователя
     */

    @ApiOperation("Выводит список избранных площадок пользователя")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @GetMapping("/favorites")
    public ResponseEntity<Set<SportGroundDto>> readFavoriteSportGroundsByAuth(Authentication authentication) {
        return ResponseEntity.ok(sportGroundService.getFavoriteSportGroundsByAuth(authentication));
    }

    @ApiOperation("Добавляет площадку в список избранных")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @PostMapping("/favorites/{id}")
    public boolean addSportGroundsToFavoritesByAuthAndId(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long id,
            Authentication authentication) {
        return sportGroundService.addFavoriteSportGroundByAuthAndId(authentication, id);
    }

    @ApiOperation("Удаляет площадку из списка избранных по индексу площадки")
    @Secured(value = {ROLE_ADMIN, ROLE_USER})
    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<?> deleteSportGroundFromFavoritesAndId(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long id,
            Authentication authentication) {
        return sportGroundService.deleteFavoriteSportGroundByAuthAndId(authentication, id) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    /*
     * Методы работы с Отзывами пользователя о спортивных площадках
     */

    @ApiOperation(value = "Отображает отзыв по индентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @GetMapping("/{sportGroundsId}/reviews/{reviewId}")
    public ResponseEntity<SportGroundReviewDto> readReview(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundsId,
            @ApiParam(value = PAR_REVIEWS_ID) @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.readReviewDtoById(reviewId, sportGroundsId));
    }

    @ApiOperation(value = "Отображает отзывы спортивной площадки")
    @ApiResponses(value = {@ApiResponse(code = 200, message = SUCCESSFUL_REQUEST)})
    @GetMapping("/{sportGroundsId}/reviews")
    public ResponseEntity<List<SportGroundReviewDto>> readAllReviews(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundsId) {
        return ResponseEntity.ok(reviewService.readAllReviewsDtoBySportGround(sportGroundsId));
    }

    @ApiOperation(value = "Создает новый отзыв пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{sportGroundsId}/reviews")
    public ResponseEntity<SportGroundReviewDto> createReview(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundsId,
            @ApiParam(value = PAR_REVIEWS_DTO) @Valid @RequestBody ReviewRequest request,
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
    @PatchMapping("/{sportGroundsId}/reviews/{reviewId}")
    public ResponseEntity<SportGroundReviewDto> updateReview(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundsId,
            @ApiParam(value = PAR_REVIEWS_ID) @PathVariable Long reviewId,
            @ApiParam(value = PAR_REVIEWS_DTO) @Valid @RequestBody ReviewRequest request,
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
    @DeleteMapping("/{sportGroundId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundId,
            @ApiParam(value = PAR_REVIEWS_ID) @PathVariable Long reviewId,
            Authentication authentication) {
        return reviewService.deleteById(reviewId, sportGroundId, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    /*
     * Методы получения информации о площадке
     */
    @ApiOperation(value = "Отображает подписчиков площадки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{sportGroundId}/subscribers")
    public ResponseEntity<Set<UserDto>> readSubscribers(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundId) {
        return ResponseEntity.ok(sportGroundService.getSubscribersBySportGroundId(sportGroundId));
    }


    //todo починить метод, сделать преобразования числа в enum и в String
    @ApiOperation(value = "Отображает инфраструктуру площадки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class)
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{sportGroundId}/infrastructures")
    public ResponseEntity<Set<String>> readInfrastructures(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundId) {
        return ResponseEntity.ok(sportGroundService.getInfrastructuresBySportGroundId(sportGroundId));
    }

    @ApiOperation(value = "Отображает список мероприятий на площадке")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @GetMapping("/{sportGroundId}/events/{pageNumber}")
    public ResponseEntity<List<EventDto>> readEvents(
            @ApiParam(value = PAR_SPORTGROUND_ID) @PathVariable Long sportGroundId,
            @ApiParam(value = PAR_PAGE_NUMBER, defaultValue = "0") @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(sportGroundService.getEventsBySportGroundId(sportGroundId, pageNumber));
    }
}
