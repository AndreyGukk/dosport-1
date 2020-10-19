package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

/**
 * Сущность Спортивные навыки пользователя
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "users_sport_types")
public class UserSportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "users")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "sport_types")
    @JoinColumn(name = "sport_type_id")
    private SportType sportType;

    @Column(name = "level")
    private Byte level;
}
