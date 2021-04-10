package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.entities.SportType;

import java.util.Set;

/**
 * Сервис Видов спорта.
 */
public interface SportTypeService {

//    /**
//     * Выдаёт вид спорта по его id
//     *
//     * @param id вида спорта
//     * @return dto вид спорта
//     */
//    SportTypeDto getSportTypeDtoById(Short id);

    /**
     * Выдает список всех видов спорта
     *
     * @return список видов спорта
     */
    Set<String> getAllSportType();

    /**
     * Выдаёт вид спорта по его названию
     *
     * @param title название вида спорта
     * @return dto вид спорта
     */
    SportType getSportTypeByTitle(String title);

    /**
     * Выдаёт видов спорта по списку названий
     *
     * @param titles название вида спорта
     * @return dto вид спорта
     */
    Set<SportType> getAllSportTypesByTitle(Set<String> titles);

    /**
     * Добавляет вид спорта
     *
     * @param sportTitle название нового вида спорта на добавление
     * @return вид спорта, сохраненный в репозитории
     */
    Boolean addSportType(String sportTitle);

    /**
     * Удаляет вид спорта по id
     *
     * @param title наименование вида спорта
     * @return вид спорта, сохраненный в репозитории
     */
    Boolean deleteByTitle(String title);

    /*
     * Методы, относящиеся к предпочитаемым видам спорта пользователя
     */

    /**
     * Выдаёт список предпочитаемых видов спорта пользователя по данным авторизации
     *
     * @param authentication данные авторизации
     * @return список пользователей
     */
    Set<String> getAllSportTypeByAuthentication(Authentication authentication);

    /**
     * Добавляет вид спорта пользователя в список предпочитаемых видов спорта пользователя
     *
     * @param sportTypeTitle название вида спорта
     * @param authentication данные авторизации
     * @return список предпочитаемых видов спорта
     */
    boolean addSportTypeToFavorite(String  sportTypeTitle, Authentication authentication);

    /**
     * Удаляет вид спорта из списка предпочитаемых видов спорта пользователя
     *
     * @param sportTypeTitle идентификатор вида спорта
     * @param authentication данные авторизации
     * @return список предпочитаемых видов спорта
     */
    boolean deleteSportByAuthentication(String sportTypeTitle, Authentication authentication);
}
