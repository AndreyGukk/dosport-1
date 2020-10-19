package ru.dosport.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Сущность Навыки пользователя
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "user_sports")
@IdClass(UserSportKey.class)
public class UserSport {

    @Id
    @Column(name = "user_id")
    private long userId;

    @Id
    @Column(name = "sport_type_id")
    private short sportTypeId;

    private short level;
}
