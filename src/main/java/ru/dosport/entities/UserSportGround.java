package ru.dosport.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность избранные площадки пользователя
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_sportgrounds")
public class UserSportGround {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "user_id")
    private long userId;


    @Column(name = "sportground_id")
    private long sportGroundId;
}
