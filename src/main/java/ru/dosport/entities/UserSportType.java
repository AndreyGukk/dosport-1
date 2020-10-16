package ru.dosport.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Сущность Навыки пользователя
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "user_sport_types")
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

    @NotBlank
    @Size(max = 5)
    @Column(name = "level")
    private Byte level;


}
