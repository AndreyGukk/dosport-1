package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.Event;
import ru.dosport.entities.MessageEvent;

import java.util.List;

@Repository
public interface MessageEventRepository extends JpaRepository<MessageEvent, Long> {

    List<MessageEvent> findAllByEventId(Long eventId);
}
