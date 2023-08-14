package coworking.digitalBooking.Repository;


import coworking.digitalBooking.Entities.CoworkingFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoworkingFacilityRepository extends JpaRepository<CoworkingFacility, Long>{

}
