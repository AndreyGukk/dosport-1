package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dosport.entities.EventMember;

import java.util.List;

public interface MemberRepository extends JpaRepository<EventMember, Long> {
    List<EventMember> findAllByEventId(Long eventId);
}
