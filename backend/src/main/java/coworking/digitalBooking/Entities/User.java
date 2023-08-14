package coworking.digitalBooking.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = { "email" }) })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "lastname", nullable = false)
	private String lastname;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;

	@Column
	private boolean enabled;

	@Column
	private String verificationCode;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private Set<Rol> roles = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Reserve> reserves;

	public User(String name, String lastname, String email, String password, boolean enabled, String verificationCode, Set<Rol> roles, List<Reserve> reserves) {
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.verificationCode = verificationCode;
		this.roles = roles;
		this.reserves = reserves;
	}
}
