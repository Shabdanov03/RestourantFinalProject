package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

/**
 * Shabdanov Ilim
 **/
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {


    @Id
    @SequenceGenerator(
            name = "categories_id_gen",
            sequenceName = "categories_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "categories_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category",cascade = ALL)
    private List<SubCategory> subCategories;
}
