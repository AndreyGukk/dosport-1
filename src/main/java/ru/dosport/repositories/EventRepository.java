package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.Event;
import ru.dosport.entities.UserEvent;

import javax.persistence.JoinTable;
import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий Мероприятий
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

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
     *
     * @param userId -id пользователя
     * @param from - дата начала интервала времени
     * @param to - дата конца интервала времени
     * @return
     */

    @Query(value = "SELECT s FROM events e JOIN event_members m " +
            "ON e.id = m.event_id " +
            "JOIN sport_types s " +
            "ON s.id = e.sportType " +
            "WHERE m.user_id = :userId " +
            "AND e.date BETWEEN  :from  AND  :to ", nativeQuery = true)
    List <UserEvent> findAllByUserIdAndTimeFromTo(Long userId, LocalDate from, LocalDate to);
}
