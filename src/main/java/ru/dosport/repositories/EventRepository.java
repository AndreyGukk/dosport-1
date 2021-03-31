package ru.dosport.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.Event;


import java.util.List;

/**
 * Репозиторий Мероприятий
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllBySportGround(Long sportGroundId, Pageable pageable);
}
