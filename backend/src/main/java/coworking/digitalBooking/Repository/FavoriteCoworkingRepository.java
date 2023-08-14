package coworking.digitalBooking.Repository;

import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.FavoriteCoworking;
import coworking.digitalBooking.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteCoworkingRepository extends JpaRepository<FavoriteCoworking, Long> {

    List<FavoriteCoworking>findFavoriteCoworkingByUser(User user);

    FavoriteCoworking findFavoriteCoworkingByUserAndCoworking(User user, Coworking coworking);

}
