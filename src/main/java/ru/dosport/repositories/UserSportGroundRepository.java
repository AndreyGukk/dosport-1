package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.UserSportGround;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSportGroundRepository extends JpaRepository<UserSportGround, Long> {
    /**
     * Поиск списка избранных площадок по  userId
     */
    @Query(value = "SELECT s FROM user_sportgrounds s WHERE s.user_id = :userId", nativeQuery = true)
    List<UserSportGround> findAllByUserId(long userId);

    /**
     * Удаление площадки из избранного
     */
    @Modifying
    @Query //(value = "DELETE s FROM user_sportgrounds s WHERE s.user_id = ?1 AND s.sportground_id = ?2", nativeQuery = true)
    @Transactional
    void deleteByUserIdAndSportGroundId(long userId, long sportGroundId);

    /**
     * Найти сочетание площадка + пользователь
     */
    @Query (value = "DELETE s FROM user_sportgrounds s WHERE s.user_id = ?1 AND s.sportground_id = ?2", nativeQuery = true)
    Optional<UserSportGround> findByUserIdAndSportGroundId(long userId, long sporeGroundId);
}
