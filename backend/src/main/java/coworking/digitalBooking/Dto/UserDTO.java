package coworking.digitalBooking.Dto;


import coworking.digitalBooking.Entities.Rol;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private long id;
	private String name;
	private String lastname;
	private String email;
	private String password;
	private Set<Rol> roles = new HashSet<>();
	private boolean enabled;
	private String verificationCode;
}
