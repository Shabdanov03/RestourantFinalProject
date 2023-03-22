package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Restaurant {
    @Id
    @SequenceGenerator(
            name = "restaurant_id_gen",
            sequenceName = "restaurant_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "restaurant_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant",cascade = ALL)
    private List<MenuItem> menuItems;
    @OneToMany(mappedBy = "restaurant",cascade = ALL)
    private List<User> user;




}