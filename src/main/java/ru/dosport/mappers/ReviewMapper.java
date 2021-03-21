package ru.dosport.mappers;

import org.mapstruct.*;
import ru.dosport.dto.ReviewDto;
import ru.dosport.entities.Review;

import java.util.List;

/**
 * Маппер, преобразующий классы Review и ReviewDto друг в друга
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mappings({
            @Mapping(target = "reviewId", source = "entity.id"),
            @Mapping(target = "userId", source = "entity.user.id"),
            @Mapping(target = "text", source = "entity.text"),
            @Mapping(target = "username", source = "entity.user.username"),
            @Mapping(target = "sportGroundId", source = "entity.sportGroundId"),
            @Mapping(target = "date", source = "entity.date", dateFormat = "dd-MM-yyyy")
    })
    ReviewDto mapEntityToDto(Review entity);

    List<ReviewDto> mapEntityToDto(List<Review> entities);

    @Mappings({
            @Mapping(target = "id", source = "dto.reviewId"),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "text", source = "dto.text"),
            @Mapping(target = "sportGroundId", source = "dto.sportGroundId"),
            @Mapping(target = "date", source = "dto.date", dateFormat = "dd-MM-yyyy")
    })
    Review mapDtoToEntity(ReviewDto dto);
}
