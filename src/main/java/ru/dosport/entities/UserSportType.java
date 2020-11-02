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
@Table(name = "user_sports")
@IdClass(UserSportKey.class)
public class UserSportType {

    @Id
    @Column(name = "user_id")
    private long userId;

    @Id
    @Column(name = "sport_type_id")
    private short sportTypeId;

    private short level;
}
