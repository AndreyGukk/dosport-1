package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.SportGroundReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<SportGroundReview, Long> {

    List<SportGroundReview> findAllBySportGroundId(Long sportGroundId);

    Optional<SportGroundReview> findByIdAndUserId(Long reviewId, Long userId);
}
