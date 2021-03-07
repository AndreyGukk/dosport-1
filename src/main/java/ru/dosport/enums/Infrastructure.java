package ru.dosport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.dosport.entities.SportGround;

import javax.persistence.*;
import java.util.List;

/**
 * Перечисление вариантов инфраструктуры спортивных площадок
 */
@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "infrastructures")
public enum Infrastructure {

    NOT_SELECTED    ("Не выбрано", 0),
    LOCKER_ROOM     ("Раздевалка", 1),
    PARKING         ("Парковка", 2),
    STANDS          ("Трибуны", 3),
    SHOWER          ("Душ", 4),
    LIGHTNING       ("Освещение", 5),
    STORAGE_ROOM    ("Камера хранения", 6),
    EQUIPMENT_RENTAL("Аренда оборудования", 7),
    SAUNA           ("Сауна", 8);

    @Transient
    private final String description;

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "sportground_infrastructures",
            joinColumns = @JoinColumn(name = "infrastructure_id"),
            inverseJoinColumns = @JoinColumn(name = "sportground_id"))
    private List<SportGround> sportGrounds;

    Infrastructure(String description, Integer id) {
        this.description = description;
        this.id = id;
    }
}
