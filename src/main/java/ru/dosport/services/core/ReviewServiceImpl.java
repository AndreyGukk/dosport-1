package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dosport.dto.SportGroundReviewDto;
import ru.dosport.dto.ReviewRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.entities.SportGroundReview;
import ru.dosport.entities.User;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;
import ru.dosport.helpers.Roles;
import ru.dosport.mappers.ReviewMapper;
import ru.dosport.mappers.UserMapper;
import ru.dosport.repositories.ReviewRepository;
import ru.dosport.services.api.ReviewService;
import ru.dosport.services.api.SportGroundService;
import ru.dosport.services.api.UserService;

import java.time.LocalDate;
import java.util.List;

import static ru.dosport.helpers.InformationMessages.DATA_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final ReviewMapper mapper;
    private final UserService userService;
    private final SportGroundService groundService;
    private final UserMapper userMapper;

    @Override
    public SportGroundReviewDto readReviewDtoById(Long reviewId, Long sportGroundId) {
        var review = findById(reviewId);
        if (!review.getSportGroundId().equals(sportGroundId)) {
            throw new DataBadRequestException("Спортивная площадка указана не корректно");
        }
        return mapper.mapEntityToDto(review);
    }

    @Override
    public List<SportGroundReviewDto> readAllReviewsDtoBySportGround(Long sportGroundId) {
        return mapper.mapEntityToDto(repository.findAllBySportGroundId(sportGroundId));
    }

    @Transactional
    @Override
    public SportGroundReviewDto saveReview(Long sportGroundId, ReviewRequest request, Authentication authentication) {
        if (groundService.exists(sportGroundId)) {
            var review = SportGroundReview.builder()
                    .user(userService.getByAuthentication(authentication))
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
    public SportGroundReviewDto updateReview(Long reviewId, Long sportGroundId, ReviewRequest request, Authentication authentication) {
        if (groundService.exists(sportGroundId) && repository.existsById(reviewId)) {
            if (isAuthorReview(reviewId, authentication)) {
                var review = findById(reviewId);
                var user = userService.getDtoByAuthentication(authentication);
                review.setText(request.getText());
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

    private SportGroundReview findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(DATA_NOT_FOUND_BY_ID, id)));
    }

    private boolean isAuthorReview(Long reviewId, Authentication authentication) {
        if (repository.findByIdAndUserId(reviewId, userService.getIdByAuthentication(authentication)).isPresent()) {
            return true;
        } else throw new AccessDeniedException("Пользователь не является автором отзыва");
    }
}
