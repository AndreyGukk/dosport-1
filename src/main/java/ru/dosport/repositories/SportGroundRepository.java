package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.SportGround;

import java.util.List;

/**
 * Репозиторий Спортивных площадок
 */
@Repository
public interface SportGroundRepository extends JpaRepository<SportGround, Long> {

    List<SportGround> findAllByCity(String city);

    List<SportGround> findAllBySportTypes(Short sportTypes);

    List<SportGround> findAllByCityAndSportTypes(String city, Short sportTypes);
}
