package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dosport.enums.AuthorityType;
import ru.dosport.enums.Gender;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * Сущность Пользователь
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Никнейм
    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String username;

    // Пароль
    @Column(name = "password", nullable = false, length = 150)
    private String password;

    // Пользователь активен (true) или заблокирован (false)
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    // Ключ активации пользователя
    @Column(name = "activation_code")
    private String activationCode;

    // Наименивание соцсети зарегистрированного пользователя
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "authority_type")
    private AuthorityType authorityType;

    // Идентификатор пользователя в соц. сети
    @Column(name = "social_id")
    private String socialId;

    // Список ролей
    @ManyToMany(fetch = EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();

    // Дата рождения
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    // Пол
    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    // Личная информация
    @Column(name = "info", length = 150)
    private String info;

    // Ссылка на адрес фотографии
    @Column(name = "photo_link", length = 150)
    private String photoLink;

    // Список подписок - пользователей, на которых подписался данный пользователь
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    private Set<User> subscriptions;

    // Список подписчиков - пользователей, которые подписаны на данного пользователя
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> subscribers;

    // Список избранных площадок
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_sportgrounds",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sportground_id"))
    private Set<SportGround> sportGrounds;

    // Список видов спорта
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_sports",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_type_id"))
    private Set<SportType> sports;

    // Список мероприятий в которых участвует пользователь
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;

    // Список мероприятий в которые пользователь был приглашен
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_invitations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> eventInvitations;
}
