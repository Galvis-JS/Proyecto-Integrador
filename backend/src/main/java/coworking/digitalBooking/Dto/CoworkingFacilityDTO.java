package coworking.digitalBooking.Dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoworkingFacilityDTO {
    private long id;
    @JsonIgnore
    private CoworkingDTO coworking;
    private FacilityDTO facility;

}
