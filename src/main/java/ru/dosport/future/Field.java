package ru.dosport.future;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность Спортивная площадка
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "fields")
public class Field {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Адрес площадки
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    // Тип площадки
    // TODO ссылка на таблицу типы площадки
    @Column(name = "type", nullable = false, unique = true)
    private String type;

    // TODO дописать сущность
}
