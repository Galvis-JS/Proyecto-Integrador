package coworking.digitalBooking.Repository;

import coworking.digitalBooking.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {

    public interface RatingResult {
        Double getRating();
        Long getValoraciones();
    }

    @Query("SELECT AVG(r.puntuacion) as rating, COUNT(r) as valoraciones " +
            "FROM Rating r " +
            "WHERE r.idCoworking = :idCoworking " +
            "GROUP BY r.idCoworking")

    RatingResult getRatingByIdCoworking(@Param("idCoworking") Long idCoworking);

}