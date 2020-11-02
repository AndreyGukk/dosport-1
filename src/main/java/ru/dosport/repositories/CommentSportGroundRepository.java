package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.CommentSportGround;

@Repository
public interface CommentSportGroundRepository extends JpaRepository<CommentSportGround, Long> {
}
