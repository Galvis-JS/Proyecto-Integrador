package coworking.digitalBooking.Dto;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	private String email;
	private String password;

}
