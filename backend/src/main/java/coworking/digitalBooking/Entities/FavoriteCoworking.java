package coworking.digitalBooking.Entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorites", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "coworking_id", "user_id" })
})
public class FavoriteCoworking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "coworking_id", nullable = false)
    private Coworking coworking;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
