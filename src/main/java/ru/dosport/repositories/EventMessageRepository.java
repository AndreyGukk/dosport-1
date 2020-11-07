package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.EventMessage;

import java.util.List;

@Repository
public interface EventMessageRepository extends JpaRepository<EventMessage, Long> {

    List<EventMessage> findAllByEventId(Long eventId);
}
