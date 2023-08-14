package coworking.digitalBooking.Entities;

import lombok.*;
import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cities",uniqueConstraints = { @UniqueConstraint(columnNames = { "name" })})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCity;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "country", nullable = false)
    private String country;


}
