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
@Table(name = "user_SportType")
public class UserSportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinColumn(name = "sportType_id")
    private SportType sportType;

    @NotBlank
    @Size(max = 5)
    @Column(name = "level")
    private Byte level;


}
