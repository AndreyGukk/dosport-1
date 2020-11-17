package ru.dosport.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.dosport.dto.ReviewDto;
import ru.dosport.dto.ReviewRequest;
import ru.dosport.services.api.ReviewService;

import java.util.List;

import static ru.dosport.helpers.Roles.ROLE_ADMIN;
import static ru.dosport.helpers.Roles.ROLE_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sportgrounds", produces = "application/json")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{sportGroundsId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long sportGroundsId, @PathVariable Long reviewId) {
        return reviewService.readReviewDtoById(reviewId, sportGroundsId);
    }

    @GetMapping("/{sportGroundsId}/reviews")
    public ResponseEntity<List<ReviewDto>> getAllReviews(@PathVariable Long sportGroundsId) {
        return reviewService.readAllReviewsDtoBySportGround(sportGroundsId);
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PostMapping("/{sportGroundsId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable Long sportGroundsId, @RequestBody ReviewRequest request,
                                                  Authentication authentication) {
        return reviewService.saveReview(sportGroundsId, request, authentication);
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @PutMapping("/{sportGroundsId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long sportGroundsId, @PathVariable Long reviewId,
                                                  @RequestBody ReviewRequest request, Authentication authentication) {
        return reviewService.updateReview(sportGroundsId, reviewId, request, authentication);
    }

    @Secured(value = {ROLE_USER, ROLE_ADMIN})
    @DeleteMapping("/{sportGroundsId}/review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long sportGroundsId, @PathVariable Long reviewId,
                                          Authentication authentication) {
        return reviewService.deleteById(reviewId, sportGroundsId, authentication);
    }
}
