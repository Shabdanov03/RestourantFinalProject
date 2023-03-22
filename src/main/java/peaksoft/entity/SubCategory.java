package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

/**
 * Shabdanov Ilim
 **/
@Entity
@Table(name = "sub_categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubCategory {

        @Id
        @SequenceGenerator(
                name = "sub_category_id_gen",
                sequenceName = "sub_category_id_seq",
                allocationSize = 1)
        @GeneratedValue(
                generator = "sub_category_id_gen",
                strategy = GenerationType.SEQUENCE
        )
        private Long id;
        private String name;
        @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
        private Category category;
        @OneToMany(mappedBy = "subCategory",cascade = ALL)
        private List<MenuItem> menuItems;
}
