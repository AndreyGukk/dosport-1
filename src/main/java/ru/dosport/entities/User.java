package ru.dosport.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.FetchType.EAGER;

/**
 * Сущность Пользователь
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Логин == e-mail
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // Пароль
    @Column(name = "password", nullable = false)
    private String password;

//    // Пользователь активен (true) или заблокирован (false)
//    @Column(name = "enabled", nullable = false)
//    private boolean enabled;

    // Список ролей
    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_authorities",
            // Внешний ключ для User в в таблице users_authorities
            joinColumns = @JoinColumn(name = "user_id"),
            // Внешний ключ для другой стороны, User в таблице users_authorities
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities = new ArrayList<>();


    // День рождения
    @Column(name = "birthday")
    private LocalDate birthday;

    // Имя
    @Column(name = "first_name")
    private String firstName;

    // Фамилия
    @Column(name = "last_name")
    private String lastName;

    // Пол
    @Column(name = "gender")
    private String gender;

    // информация "о себе"
    @Column(name = "info")
    private String info;

    // Ссылка на адрес фотографии
    @Column(name = "photo_link")
    private String photoLink;

//    // Список увлечений
//    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "users_kindsOfSport",
//            // Внешний ключ для User в в таблице users_authorities
//            joinColumns = @JoinColumn(name = "user_id"),
//            // Внешний ключ для другой стороны, User в таблице users_authorities
//            inverseJoinColumns = @JoinColumn(name = "kindsOfSport_id"))
//    private Map<KindOfSport, Integer> kindsOfSport = new HashMap<>();
}
