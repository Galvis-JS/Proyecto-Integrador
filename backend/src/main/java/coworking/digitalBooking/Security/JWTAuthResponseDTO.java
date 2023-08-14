package coworking.digitalBooking.Security;


import coworking.digitalBooking.Dto.UserDTO;
import coworking.digitalBooking.Entities.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JWTAuthResponseDTO {

    private String tokenAccess;
    private String tokenType = "Bearer";
    private User user;


    public JWTAuthResponseDTO(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public JWTAuthResponseDTO(String tokenAccess, User user) {
        this.tokenAccess = tokenAccess;
        this.user = user;
    }
}
