package coworking.digitalBooking.Dto;

import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.User;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteCoworkingDTO {
    private Long id;
    private CoworkingDTO coworking;
    private UserDTO user;
}
