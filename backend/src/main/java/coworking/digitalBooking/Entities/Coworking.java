package coworking.digitalBooking.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coworking.digitalBooking.Repository.RatingRepository.RatingResult;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Coworking",uniqueConstraints = { @UniqueConstraint(columnNames = { "name" })})
public class Coworking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCoworking;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Transient
    private RatingResult ratingResult;

    // Se agregan 3 columnas, una para cada política
    @Column(name= "coworking_rules_policy", columnDefinition = "TEXT")
    private String coworkingRulesPolicy;

    @Column(name= "health_safety_policy", columnDefinition = "TEXT")
    private String healthSafetyPolicy;

    @Column(name= "cancellation_policy", columnDefinition = "TEXT")
    private String cancellationPolicy;

    @ManyToOne
    @JoinColumn(name = "idCity")
    private City city;
    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoworkingFacility> coworkingFacilities;

    @JsonIgnore
    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL)
    private List<Reserve> reserves;


    @Override
    public String toString(){
        return  "Category =  " + category.getName() +
                ", Name Coworking = " + name +
                ", Address = " + address;
    }

}