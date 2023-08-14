package coworking.digitalBooking.Repository;

import coworking.digitalBooking.Entities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility,Long> {

    Facility findByName(String name);

}
