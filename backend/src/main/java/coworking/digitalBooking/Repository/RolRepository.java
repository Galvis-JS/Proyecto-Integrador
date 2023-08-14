package coworking.digitalBooking.Repository;


import coworking.digitalBooking.Entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long>{

	public Optional<Rol> findByName(String name);
	
}
