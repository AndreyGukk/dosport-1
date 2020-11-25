package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dosport.entities.EventMember;

import java.util.List;
import java.util.Optional;

public interface EventMemberRepository extends JpaRepository<EventMember, Long> {

    Optional<EventMember> findByUserIdAndEventId(Long userId, Long eventId);

    List<EventMember> findAllByEventId(Long eventId);

    List<Long> findAllEventIdByUserId(Long userId);
}
