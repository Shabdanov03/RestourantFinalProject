package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

/**
 * Shabdanov Ilim
 **/
@Entity
@Table(name = "menuItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MenuItem {
    @Id
    @SequenceGenerator(
            name = "menuItem_id_gen",
            sequenceName = "menuItem_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "menuItem_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String image;
    private int price;
    private String description;
    private boolean isVegetarian;
    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private SubCategory subCategory;
    @OneToOne(mappedBy = "menuItem",cascade = ALL,fetch = FetchType.EAGER)
    private StopList stopList;
    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private Restaurant restaurant;
    @ManyToMany(cascade = ALL)
    private List<Cheque> cheques;

    public void addCheque(Cheque cheque){
        if (cheques == null){
       cheques = new ArrayList<>();
        }
        cheques.add(cheque);
    }

}
