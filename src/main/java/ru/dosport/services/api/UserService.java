package ru.dosport.services.api;

import org.springframework.security.core.Authentication;
import ru.dosport.dto.PasswordRequest;
import ru.dosport.dto.UserDto;
import ru.dosport.dto.UserRequest;
import ru.dosport.entities.User;
import ru.dosport.security.JwtUser;

import java.util.List;

/**
 * Сервис пользователей
 */
public interface UserService {

    /*
     * СОГЛАШЕНИЕ О НАИМЕНОВАНИИ МЕТОДОВ СЕРВИСОВ
     * User getById(Long id) найти объект по параметру
     * UserDto getDtoById(Long id) найти Dto объект по параметру
     * List<User> getAll() найти все объекты
     * List<UserDto> getAllDto() найти все Dto объекты
     * List<UserDto> getAllDtoByUser(UserDto userDto) найти все Dto объекты по параметру
     * UserDto update(UserDto userDto) изменить объект
     * UserDto save(UserDto userDto) сохранить объект
     * List<UserDto> saveAllDto(List<UserDto> userDtoList) сохранить список объектов
     * void delete(UserDto UserDto) удалить конкретный объект
     * void deleteById(Long id) удалить объект по параметру
     * void deleteAll(List<UserDto> userDtoList) удалить список объектов
     */

    /**
     * Найти пользователя по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    UserDto getDtoById(Long id);

    /**
     * Найти пользователя по данным авторизации
     *
     * @param authentication данные авторизации
     * @return пользователь
     */
    UserDto getDtoByAuthentication(Authentication authentication);

    /**
     * Найти пользователя по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    User getById(Long id);

    /**
     * Найти пользователя по данным авторизации
     *
     * @param authentication данные авторизации
     * @return пользователь
     */
    User getByAuthentication(Authentication authentication);

    /**
     * Найти Jwt пользователя
     *
     * @param username логин пользователя
     * @return Jwt Пользователь
     */
    JwtUser getJwtByUsername(String username);

    /**
     * Получить id пользователя по авторизации
     *
     * @param authentication данные авторизации
     * @return id Пользователя
     */
    Long getIdByAuthentication(Authentication authentication);

    /**
     * Создать нового пользователя
     *
     * @param userRequest запрос с данными пользователя
     * @return новый пользователь, сохраненный в репозитории
     */
    UserDto save(UserRequest userRequest);

    /**
     * Сохранить данные пользователя
     *
     * @param user пользователь
     * @return новый пользователь, сохраненный в репозитории
     */
    User save(User user);

    /**
     * Изменить данные пользователя
     *
     * @param userDto пользователь с измененными данными
     * @param authentication данные авторизации
     */
    UserDto update(UserDto userDto, Authentication authentication);

    /**
     * Изменить пароль пользователя
     *
     * @param passwordRequest данные для изменения пароля
     * @param authentication данные авторизации
     * @return измененен ли пользователь
     */
    boolean updatePassword(PasswordRequest passwordRequest, Authentication authentication);

    /**
     * Удалить пользователя по его идентификатору
     *
     * @param authentication данные авторизации
     * @return существует ли ли пользователь
     */
    boolean deleteByAuthentication(Authentication authentication);

    /**
     * Активировать пользователя
     *
     * @param activationCode значение строки запроса валидации
     * @return значение активен ли пользователь
     */
    String activate(String activationCode);

    /*
     * Методы относящиеся к подпискам и подписчикам пользователя
     */

    /**
     * Найти список пользователей, которые добавили пользователя в друзья по данным авторизации
     *
     * @param authentication данные авторизации
     * @return список пользователей
     */
    List<UserDto> getSubscribersByAuthentication(Authentication authentication);

    /**
     * Найти список друзей пользователя по данным авторизации
     *
     * @param authentication данные авторизации
     * @return список пользователей
     */
    List<UserDto> getSubscriptionsByAuthentication(Authentication authentication);

    /**
     * Добавить пользователя в список его друзей по данным авторизации и id друга
     *
     * @param authentication данные авторизации
     * @return добавлен ли пользователь в список
     */
    boolean addSubscriptionByAuthentication(Long subscriptionId, Authentication authentication);

    /**
     * Удалить пользователя из списка его друзей по данным авторизации и id пользователя
     *
     * @param authentication данные авторизации
     * @return удален ли пользователь из списка
     */
    boolean deleteSubscriptionByAuthentication(Long subscriptionId, Authentication authentication);
}
