package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.SportType;

import java.util.Optional;

@Repository
public interface SportTypeRepository extends JpaRepository<SportType, Short> {
    Optional<SportType> findByTitle(String title);
}
