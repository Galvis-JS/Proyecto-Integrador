package coworking.digitalBooking.Entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 60, name = "name", nullable = false)
	private String name;

	public Rol(String name) {
		this.name = name;
	}
}
