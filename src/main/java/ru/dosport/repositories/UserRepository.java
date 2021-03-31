package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * СОГЛАШЕНИЕ О НАИМЕНОВАНИИ МЕТОДОВ РЕПОЗИТОРИЕВ
     * Optional<Object> findById(Long id) найти объект по параметру
     * List<Object> findAll() найти все объекты
     * List<Object> findAllByEnabled(boolean enabled) найти все объекты по параметру
     * void delete(Object object) удалить конкретный объект
     * void deleteById(Long id) удалить объект по параметру
     * void deleteAll(List<Object> objects) удалить список объектов
     * Object save(Object object) сохранить объект
     * List<Object> saveAll(List<Object> objects) сохранить список объектов
     */

    /**
     * Найти пользователя по его логину
     */
    Optional<User> findByUsername(String username);

    /**
     * Найти пользователя по его UUID
     */
    Optional<User> findByUuid(String uuid);

    /**
     * Найти список пользователей, которые добавили пользователя в друзья
     */
    @Query (value = "SELECT * FROM users u JOIN user_subscriptions us " +
            "ON u.id = us.user_id WHERE us.subscription_id = ?1", nativeQuery = true)
    List<User> findSubscribersByUserId(long userId);
}
