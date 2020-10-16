package ru.dosport.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Сущность Навыки пользователя
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
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
