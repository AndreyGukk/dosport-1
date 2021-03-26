package ru.dosport.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.dosport.entities.Event;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Спецификации запросов в репозиторий мероприятий
 */
public class EventSpecifications {

    public static Specification<Event> eventHasSearchCriteria(EventSearchCriteria searchCriteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchCriteria.getCreationDateTimeFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDateTime"), searchCriteria.getCreationDateTimeFrom()));
            }
            if (searchCriteria.getCreationDateTimeTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("creationDateTime"), searchCriteria.getCreationDateTimeTo()));
            }
            if (searchCriteria.getStartDateTimeFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDateTime"), searchCriteria.getStartDateTimeFrom()));
            }
            if (searchCriteria.getEndDateTimeTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDateTime"), searchCriteria.getEndDateTimeTo()));
            }
            if (searchCriteria.getSportTypeId() != null && searchCriteria.getSportTypeId() > 0) {
                predicates.add(cb.equal(root.get("sportType").get("id"), searchCriteria.getSportTypeId()));
            }
            if (searchCriteria.getSportGroundId() != null && searchCriteria.getSportGroundId() > 0) {
                predicates.add(cb.equal(root.get("sportGround").get("id"), searchCriteria.getSportGroundId()));
            }
            if (searchCriteria.getOrganizerId() != null && searchCriteria.getOrganizerId() > 0) {
                predicates.add(cb.equal(root.get("organizer").get("id"), searchCriteria.getOrganizerId()));
            }
            if (searchCriteria.getIsPrivate() != null) {
                predicates.add(cb.equal(root.get("isPrivate"), searchCriteria.getIsPrivate()));
            }
            if (searchCriteria.getMinPrice() != null && searchCriteria.getMinPrice() >= 0 ) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), searchCriteria.getMinPrice()));
            }
            if (searchCriteria.getMaxPrice() != null && searchCriteria.getMaxPrice() >= 0) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), searchCriteria.getMaxPrice()));
            }
            return cb.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
