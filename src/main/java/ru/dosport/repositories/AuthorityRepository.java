package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.Authority;

/**
 * Репозиторий ролей пользователей
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    /**
     * Найти роль пользователя по ее имени
     */
    Authority findByAuthority(String authority);
}
