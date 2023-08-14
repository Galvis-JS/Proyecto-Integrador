package coworking.digitalBooking.Repository;

import coworking.digitalBooking.Entities.City;
import coworking.digitalBooking.Entities.Coworking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

    City findByName(String name);

}
