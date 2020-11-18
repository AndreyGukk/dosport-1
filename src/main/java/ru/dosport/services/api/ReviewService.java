package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.*;
import ru.dosport.exceptions.DataBadRequestException;
import ru.dosport.exceptions.DataNotFoundException;

import java.util.List;

public interface ReviewService {

    /**
     * Возращает рецензию по её идентификатору
     * @param reviewId идентификатор рецензии
     * @param sportGroundId идентификатор площадки
     * @exception DataBadRequestException, площадка не указана
     * @exception DataNotFoundException, не найдена площадка
     * @return ReviewDto
     */
    ReviewDto readReviewDtoById(Long reviewId, Long sportGroundId);

    /**
     * Возращает рецензии по идентификатору площадки
     * @param sportGroundId идентификатор площадки
     * @return List<ReviewDto>
     */
    List<ReviewDto> readAllReviewsDtoBySportGround(Long sportGroundId);

    /**
     * Сохраняет новую рецензию
     * @param sportGroundId идентификатор площадки
     * @param request запрос (текст рецензии)
     * @param authentication данные авторизации
     * @exception DataBadRequestException, не найдена спортивная площадка
     * @return dto новой рецензии
     */
    ReviewDto saveReview(Long sportGroundId, ReviewRequest request, Authentication authentication);

    /**
     * Обновляет рецензию (текст, имя юзера)
     * @param reviewId идентификатор рецензии
     * @param sportGroundId идентификатор площадки
     * @param request запрос (текст рецензии)
     * @param authentication данные авторизации
     * @exception DataNotFoundException, не получилось найти рецензию
     * @return dto обновлённой рецензии
     */
    ReviewDto updateReview(Long reviewId, Long sportGroundId, ReviewRequest request, Authentication authentication);

    /**
     * Удаление рецензии (только автор рецензии)
     * @param reviewId идентификатор рецензии
     * @param sportGroundId идентификатор площадки
     * @param authentication данные авторизации
     * @exception DataNotFoundException, не получилось найти рецензию
     * @return true, false - удаленна ли сущность
     */
    Boolean deleteById(Long reviewId, Long sportGroundId, Authentication authentication);
}
