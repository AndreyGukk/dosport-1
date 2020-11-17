package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.ReviewDto;
import ru.dosport.dto.ReviewRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.entities.Review;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.ReviewMapper;
import ru.dosport.repositories.ReviewRepository;
import ru.dosport.services.api.ReviewService;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.UserService;

import java.time.LocalDate;
import java.util.List;

import static ru.dosport.helpers.Messages.DATA_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    private final ReviewMapper mapper;

    private final UserService userService;
    private final SportGroundService groundService;

    @Override
    public ResponseEntity<ReviewDto> readReviewDtoById(Long reviewId, Long sportGroundId) {
        var review = findById(reviewId);
        return review.getSportGroundId().equals(sportGroundId) ?
                ResponseEntity.ok(mapper.mapEntityToDto(review)) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<List<ReviewDto>> readAllReviewsDtoBySportGround(Long sportGroundId) {
        return ResponseEntity.ok(mapper.mapEntityToDto(repository.findAllBySportGroundId(sportGroundId)));
    }

    @Transactional
    @Override
    public ResponseEntity<ReviewDto> saveReview(Long sportGroundId, ReviewRequest request, Authentication authentication) {
        if (groundService.exists(sportGroundId)) {
            var user = userService.getDtoByAuthentication(authentication);
            var review = Review.builder()
                    .userId(user.getId())
                    .userFullName(concatenationUserName(user))
                    .text(request.getText())
                    .sportGroundId(sportGroundId)
                    .date(LocalDate.now())
                    .build();
            return ResponseEntity.ok(mapper.mapEntityToDto(repository.save(review)));
        } else return ResponseEntity.badRequest().build();
    }

    @Transactional
    @Override
    public ResponseEntity<ReviewDto> updateReview(Long reviewId, Long sportGroundId, ReviewRequest request, Authentication authentication) {
        if (groundService.exists(sportGroundId) && repository.existsById(reviewId)) {
            if (isAuthorReview(reviewId, authentication)) {
                var review = findById(reviewId);
                var user = userService.getDtoByAuthentication(authentication);
                review.setText(request.getText());
                review.setUserFullName(concatenationUserName(user));
                return ResponseEntity.ok(mapper.mapEntityToDto(repository.save(review)));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> deleteById(Long reviewId, Long sportGroundId, Authentication authentication) {
        if (groundService.exists(sportGroundId) && repository.existsById(reviewId)) {
            if (isAuthorReview(reviewId, authentication) || Roles.hasAuthenticationRoleAdminOrThrowException(authentication)) {
                var review = findById(reviewId);
                repository.deleteById(review.getId());
                return repository.existsById(review.getId()) ?
                        ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.noContent().build();
    }

    private Review findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    private boolean isAuthorReview(Long reviewId, Authentication authentication) {
        if (repository.findByIdAndUserId(reviewId, userService.getIdByAuthentication(authentication)).isPresent()) {
            return true;
        } else throw new AccessDeniedException("Пользователь не является автором отзыва");
    }

    private String concatenationUserName(UserDto userDto) {
        return String.format("%s %s", userDto.getFirstName(), userDto.getLastName());
    }
}
