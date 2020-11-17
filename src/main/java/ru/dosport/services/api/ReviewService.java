package ru.dosport.services.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.dosport.dto.*;

import java.util.List;

public interface ReviewService {

    ResponseEntity<ReviewDto> readReviewDtoById(Long reviewId, Long sportGroundId);

    ResponseEntity<List<ReviewDto>> readAllReviewsDtoBySportGround(Long sportGroundId);

    ResponseEntity<ReviewDto> saveReview(Long sportGroundId, ReviewRequest request, Authentication authentication);

    ResponseEntity<ReviewDto> updateReview(Long reviewId, Long sportGroundId, ReviewRequest request, Authentication authentication);

    ResponseEntity<?> deleteById(Long reviewId, Long sportGroundId, Authentication authentication);
}
