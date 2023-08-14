package coworking.digitalBooking.Entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coworking_facility")
public class CoworkingFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "coworking_id", nullable = false)
    private Coworking coworking;
    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

}
