package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.UserSportType;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий видов спорта юзера
 */
@Repository
public interface UserSportTypeRepository extends JpaRepository<UserSportType, Short> {

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
     * Поиск по составному первичному ключу userId + sportTypeId
     */
    @Query(value = "SELECT s FROM user_sports s WHERE s.user_id = :userId AND s.sport_type_id = :sportTypeId", nativeQuery = true)
    Optional<UserSportType> findByUserIdAndSportTypeId(long userId, short sportTypeId);


    /**
     * Поиск списка навыков по userId
     *
     */
    @Query(value = "SELECT s FROM user_sports s WHERE s.user_id = :userId", nativeQuery = true)
    List<UserSportType> findAllByUserId(long userId);

    /**
     * Добавление уровня владения видом спорта.
     */
    @Modifying
    @Query(value = "INSERT INTO user_sports (user_id, sport_type_id, level) VALUES (:userId, :sportTypeId,:level)", nativeQuery = true)
    @Transactional
    Optional<UserSportType> save(@Param("userId") long userId, @Param("sportTypeId") short sportTypeId, @Param("level") short level);

    /**
     * Изменение уровня владения видом спорта .
     */
    @Modifying
    @Query(value = "UPDATE user_sports SET level = :level WHERE user_id = :userId AND sport_type_id = :sportTypeId", nativeQuery = true)
    @Transactional
    Optional<UserSportType> update(@Param("userId")long userId, @Param("sportTypeId") short sportTypeId, @Param("level") short level);

    /**
     * Удаление вида спорта у пользователя.
     */
    @Modifying
    @Query(value = "DELETE s FROM user_sports s WHERE s.user_id = ?1 AND s.sport_type_id = ?2", nativeQuery = true)
    @Transactional
    void deleteBySportTypeId(Long idByAuthentication, Short sportTypeId);

}
