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
import ru.dosport.exceptions.DataBadRequestException;
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
    public ReviewDto readReviewDtoById(Long reviewId, Long sportGroundId) {
        var review = findById(reviewId);
        if (!review.getSportGroundId().equals(sportGroundId)) {
            throw new DataBadRequestException("Спортивная площадка указана не корректно");
        }
        return mapper.mapEntityToDto(review);
    }

    @Override
    public List<ReviewDto> readAllReviewsDtoBySportGround(Long sportGroundId) {
        return mapper.mapEntityToDto(repository.findAllBySportGroundId(sportGroundId));
    }

    @Transactional
    @Override
    public ReviewDto saveReview(Long sportGroundId, ReviewRequest request, Authentication authentication) {
        if (groundService.exists(sportGroundId)) {
            var user = userService.getDtoByAuthentication(authentication);
            var review = Review.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .text(request.getText())
                    .sportGroundId(sportGroundId)
                    .date(LocalDate.now())
                    .build();
            return mapper.mapEntityToDto(repository.save(review));
        }
        throw new DataBadRequestException(String.format(DATA_NOT_FOUND_BY_ID, sportGroundId));
    }

    @Transactional
    @Override
    public ReviewDto updateReview(Long reviewId, Long sportGroundId, ReviewRequest request, Authentication authentication) {
        if (groundService.exists(sportGroundId) && repository.existsById(reviewId)) {
            if (isAuthorReview(reviewId, authentication)) {
                var review = findById(reviewId);
                var user = userService.getDtoByAuthentication(authentication);
                review.setText(request.getText());
                review.setUsername(user.getUsername());
                return mapper.mapEntityToDto(repository.save(review));
            }
        }
        throw new DataNotFoundException("Отзыв не найлен");
    }

    @Override
    public Boolean deleteById(Long reviewId, Long sportGroundId, Authentication authentication) {
        if (groundService.exists(sportGroundId) && repository.existsById(reviewId)) {
            if (isAuthorReview(reviewId, authentication) || Roles.hasAuthenticationRoleAdminOrThrowException(authentication)) {
                var review = findById(reviewId);
                repository.deleteById(review.getId());
                return repository.existsById(review.getId());
            }
        }
        throw new DataNotFoundException("Отзыв не найлен");
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
}
