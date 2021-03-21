package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.SportType;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportTypeRepository extends JpaRepository<SportType, Short> {

    Optional<SportType> findByTitle(String title);

    /**
     * Выдаёт список предпочитаемых видов спорта пользователя по userId
     */
    @Query(value =
            "SELECT st.id, st.title FROM user_sports us " +
            "JOIN sport_types st ON st.id = us.sport_type_id " +
            "WHERE us.user_id = :userId", nativeQuery = true)
    List<SportType> findAllByUserId(Long userId);
}
