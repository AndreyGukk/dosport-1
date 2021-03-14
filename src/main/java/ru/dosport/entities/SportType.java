package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сущность Вид спорта
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "sport_types")
public class SportType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Short id;

    // Вид спорта
    @Column(name = "title", nullable = false, unique = true, length = 150)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "sportground_sport_types",
            joinColumns = @JoinColumn(name = "sport_type_id"),
            inverseJoinColumns = @JoinColumn(name = "sportground_id"))
    private List<SportGround> sportGrounds;

    @ManyToMany
    @JoinTable(
            name = "user_sports",
            joinColumns = @JoinColumn(name = "sport_type_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<SportGround> sports = new HashSet<>();

    public SportType(String title) {
        this.title = title;
    }
}
