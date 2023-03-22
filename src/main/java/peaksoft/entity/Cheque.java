package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

/**
 * Shabdanov Ilim
 **/
@Entity
@Table(name = "cheques")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cheque {


    @Id
    @SequenceGenerator(
            name = "cheque_id_gen",
            sequenceName = "cheque_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "cheque_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private int priceAverage;
    private LocalDate createdAt;
    @ManyToMany(mappedBy = "cheques",cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private List<MenuItem> menuItems;
    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private User user;

    public void addMenuItems(MenuItem menuItem){
        if (menuItems == null){
            menuItems = new ArrayList<>();
        }
        menuItems.add(menuItem);
    }
}
