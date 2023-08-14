package coworking.digitalBooking.Repository;

import coworking.digitalBooking.Entities.Category;
import coworking.digitalBooking.Entities.Coworking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoworkingRepository extends JpaRepository<Coworking,Long> {

    Coworking findByName(String name);
    List<Coworking> findCoworkingByCategory(Category category);
}
