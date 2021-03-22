package ru.dosport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dosport.entities.Event;
import ru.dosport.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий Мероприятий
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Найти список User по идентификатору пользователя, начальной и конечной датам
     *
     * @param userId id пользователя
     * @param from дата начала интервала времени
     * @param to дата конца интервала времени
     * @return список User
     */
    @Query(value = "SELECT s FROM events e JOIN event_users m " +
            "ON e.id = m.event_id " +
            "JOIN sport_types s " +
            "ON s.id = e.sportType " +
            "WHERE m.user_id = :userId " +
            "AND e.date BETWEEN  :from  AND  :to ", nativeQuery = true)
    List <User> findAllByUserIdAndTimeFromTo(Long userId, LocalDate from, LocalDate to);

    /**
     * Найти список событий по определенным параметрам поиска
     *
     * @param from начальная дата поиска
     * @param to конечная дата поиска
     * @param sportTypeId идентификатор вида спорта
     * @param sportGroundId идентификатор площадки
     * @param organizerId идентификатор организатора
     * @return список Event
     */
    @Query(value = "SELECT s FROM events e JOIN event_users m " +
            "ON e.id = m.event_id " +
            "JOIN sport_types s " +
            "ON s.id = e.sport_type_id " +
            "WHERE s.organizer_user_id = :organizerId " +
            "AND e.date BETWEEN  :from  AND  :to " +
            "AND e.sport_type_id = :sportTypeId" +
            "AND e.sportground_id = :sportGroundId", nativeQuery = true)
    List<Event> findAllByParams(
            LocalDate from, LocalDate to, Short sportTypeId, Long sportGroundId, Long organizerId);

    /**
     * Найти список User по идентификатору пользователя
     *
     * @param userId id пользователя
     * @return список User
     */
    @Query(value = "SELECT s FROM events e JOIN event_users m " +
            "ON e.id = m.event_id " +
            "JOIN sport_types s " +
            "ON s.id = e.sportType " +
            "WHERE m.user_id = :userId ", nativeQuery = true)
    List <User> findAllByUserId(Long userId);

    @Query(value = "SELECT s FROM events e JOIN event_users m " +
            "AND e.date BETWEEN  :from  AND  :to ", nativeQuery = true)
    List<Event> findAllByTimeFromTo(LocalDateTime from, LocalDateTime to);

    List<Event> findAllBySportGroundId(Long sportGroundId);
}
