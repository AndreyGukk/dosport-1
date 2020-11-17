package ru.dosport.services.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.dosport.dto.*;

import java.util.List;

public interface ReviewService {

    /**
     * Возращает рецензию по её идентификатору
     * @param reviewId идентификатор рецензии
     * @param sportGroundId идентификатор площадки
     * @return ResponseEntity<ReviewDto>
     */
    ResponseEntity<ReviewDto> readReviewDtoById(Long reviewId, Long sportGroundId);

    /**
     * Возращает рецензии по идентификатору площадки
     * @param sportGroundId идентификатор площадки
     * @return ResponseEntity<List<ReviewDto>>
     */
    ResponseEntity<List<ReviewDto>> readAllReviewsDtoBySportGround(Long sportGroundId);

    /**
     * Сохраняет новую рецензию
     * @param sportGroundId идентификатор площадки
     * @param request запрос (текст рецензии)
     * @param authentication данные авторизации
     * @return ResponseEntity<ReviewDto>
     */
    ResponseEntity<ReviewDto> saveReview(Long sportGroundId, ReviewRequest request, Authentication authentication);

    /**
     * Обновляет рецензию (текст, имя юзера)
     * @param reviewId идентификатор рецензии
     * @param sportGroundId идентификатор площадки
     * @param request запрос (текст рецензии)
     * @param authentication данные авторизации
     * @return ResponseEntity<ReviewDto>
     */
    ResponseEntity<ReviewDto> updateReview(Long reviewId, Long sportGroundId, ReviewRequest request, Authentication authentication);

    /**
     * Удаление рецензии (только автор рецензии)
     * @param reviewId идентификатор рецензии
     * @param sportGroundId идентификатор площадки
     * @param authentication данные авторизации
     * @return ResponseEntity.ok/badRequest/noContent. Удаление произошло успешно/возникли ошибки/ такой сущности нет
     */
    ResponseEntity<?> deleteById(Long reviewId, Long sportGroundId, Authentication authentication);
}
