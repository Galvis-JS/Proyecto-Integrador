package coworking.digitalBooking.Dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    private Long id;
    private Long idCoworking;
    private Integer puntuacion;

    public void setpuntuacion(Integer puntuacion) {
    }

    public void setidCoworking(Long idCoworking) {
    }
}
