package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.SportGroundReviewDto;
import ru.dosport.entities.SportGroundReview;

import java.util.List;

import static ru.dosport.helpers.Patterns.LOCAL_DATE_PATTERN;

/**
 * Маппер, преобразующий классы Review и ReviewDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class})
public interface ReviewMapper {

    @Mappings({
            @Mapping(target = "reviewId", source = "entity.id"),
            @Mapping(target = "username", source = "entity.user.username"),
            @Mapping(target = "sportGroundId", source = "entity.sportGroundId"),
            @Mapping(target = "date", source = "entity.date", dateFormat = LOCAL_DATE_PATTERN),
            @Mapping(target = "text", source = "entity.text"),
    })
    SportGroundReviewDto mapEntityToDto(SportGroundReview entity);

    List<SportGroundReviewDto> mapEntityToDto(List<SportGroundReview> entities);

}
