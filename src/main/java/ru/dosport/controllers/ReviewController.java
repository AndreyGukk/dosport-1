package ru.dosport.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ErrorDto;
import ru.dosport.dto.ReviewDto;
import ru.dosport.dto.ReviewRequest;
import ru.dosport.services.api.ReviewService;

import javax.validation.Valid;
import java.util.List;

import static ru.dosport.helpers.Messages.*;
import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sportgrounds", produces = "application/json")
@Api(tags = {"Контроллер Отзывов пользователя"})
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "Отображает отзыв по индентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESSFUL_REQUEST),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorDto.class),
            @ApiResponse(code = 404, message = DATA_NOT_FOUND, response = ErrorDto.class),
    })
    @GetMapping("/{sportGroundsId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long sportGroundsId, @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.readReviewDtoById(reviewId, sportGroundsId));
    }

    @ApiOperation(value = "Отображенеи отзывы спортивной площадки")
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
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long sportGroundsId, @PathVariable Long reviewId,
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
    public ResponseEntity<?> deleteReview(@PathVariable Long sportGroundsId, @PathVariable Long reviewId,
                                          Authentication authentication) {
        return reviewService.deleteById(reviewId, sportGroundsId, authentication) ?
                ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }
}
