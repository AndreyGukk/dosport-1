package ru.dosport.future;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность площадки
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "field")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // адрес
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    // тип площадки
    @Column(name = "type", nullable = false, unique = true)
    private String type;



    //todo дописать сущность, решить что в адрес идет
}
