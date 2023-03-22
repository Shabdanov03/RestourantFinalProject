package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

/**
 * Shabdanov Ilim
 **/
@Entity
@Table(name = "stop_lists")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StopList {


        @Id
        @SequenceGenerator(
                name = "stopList_id_gen",
                sequenceName = "stopList_id_seq",
                allocationSize = 1)
        @GeneratedValue(
                generator = "stopList_id_gen",
                strategy = GenerationType.SEQUENCE
        )
        private Long id;
        private String reason;
        private LocalDate date;
        @OneToOne(fetch = FetchType.LAZY,cascade = {PERSIST,MERGE,REFRESH,DETACH})
        private MenuItem menuItem;
}
