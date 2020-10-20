package ru.dosport.services.api;

import ru.dosport.entities.UserSport;

/**
 * Сервис видов спорта пользователя
 */
public interface UserSportService {

    /**
     * Найти вид спорта пользователя по идентификатору пользователя и идентификатору вида спорта
     *
     * @param userId идентификатор пользователя
     * @param sportTypeId идентификатор вида спорта
     * @return спорт пользователя или null, если репозиторий не сожержит такую запись
     */
    UserSport findByUserIdAndSportTypeId(long userId, short sportTypeId);

    /**
     * Создать вид спорта пользователя
     *
     * @param userId логин пользователя
     * @param sportTypeId логин пользователя
     * @param level логин пользователя
     * @return новый спорт пользователя, сохраненный в репозиторий
     */
    UserSport save(long userId, short sportTypeId, short level);

    /**
     * Изменить уровень владения пользователем видом спорта
     *
     * @param userId логин пользователя
     * @param sportTypeId логин пользователя
     * @param level логин пользователя
     * @return измененный вид спорт пользователя
     */
    UserSport update(long userId, short sportTypeId, short level);

    /**
     * Удаление из репозитория видов спорта пользователя
     *
     * @param userId логин пользователя
     * @return false, если строк по userId не найдено
     */
    boolean deleteByUserId(long userId);
}
