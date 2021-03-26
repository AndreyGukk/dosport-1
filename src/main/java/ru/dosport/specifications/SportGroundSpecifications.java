package ru.dosport.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.dosport.entities.SportGround;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Спецификации запросов в репозиторий мероприятий
 */
public class SportGroundSpecifications {

    public static Specification<SportGround> sportGroundHasSearchCriteria(SportGroundSearchCriteria searchCriteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // Поиск по городу и адресу
            if (searchCriteria.getCity() != null && !searchCriteria.getCity().isBlank()) {
                predicates.add(cb.like(root.get("city"), searchCriteria.getCity()));
            }
            if (searchCriteria.getAddress() != null && !searchCriteria.getAddress().isBlank()) {
                predicates.add(cb.like(root.get("address"), searchCriteria.getAddress()));
            }
            // TODO реализовать маппинг со String на Enum, добавить списки infrastructures и sportTypes
//            if (searchCriteria.getMetroStation() != null && !searchCriteria.getMetroStation().isBlank()) {
//                predicates.add(cb.equal(root.get(""), searchCriteria.getMetroStation()));
//            }
//            if (searchCriteria.getSurfaceType() != null && !searchCriteria.getSurfaceType().isBlank()) {
//                predicates.add(cb.equal(root.get(""), searchCriteria.getSurfaceType()));
//            }
//            if (searchCriteria.getInfrastructures() != null && !searchCriteria.getInfrastructures().isEmpty()) {
//                predicates.add(cb.isMember(searchCriteria.getInfrastructures(), root.get("infrastructures")));
//            }
            // Поиск по координатам
            if (searchCriteria.getMinLatitude() != null && searchCriteria.getMaxLatitude() != null) {
                predicates.add(cb.between(root.get("latitude"), searchCriteria.getMinLatitude(), searchCriteria.getMaxLatitude()));
            }
            if (searchCriteria.getMinLongitude() != null && searchCriteria.getMaxLongitude() != null) {
                predicates.add(cb.between(root.get("longitude"), searchCriteria.getMinLongitude(), searchCriteria.getMaxLongitude()));
            }
            // Поиск по цене аренды
            if (searchCriteria.getMinRentPrice() != null && searchCriteria.getMinRentPrice() >= 0) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("rentPrice"), searchCriteria.getMinRentPrice()));
            }
            if (searchCriteria.getMaxRentPrice() != null && searchCriteria.getMaxRentPrice() >= 0) {
                predicates.add(cb.lessThanOrEqualTo(root.get("rentPrice"), searchCriteria.getMaxRentPrice()));
            }
            // Является ли площадка открытой
            if (searchCriteria.getOpened() != null) {
                predicates.add(cb.equal(root.get("opened"), searchCriteria.getOpened()));
            }
            return cb.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
