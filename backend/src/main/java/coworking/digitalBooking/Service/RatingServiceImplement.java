package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.RatingDTO;
import coworking.digitalBooking.Entities.Rating;
import coworking.digitalBooking.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RatingServiceImplement implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImplement(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }

    public Rating register(Long idCoworking, Integer puntuacion) {
        Rating rating = new Rating(null, idCoworking, puntuacion);
        return ratingRepository.save(rating);
    }

    @Override
    public RatingDTO registerRating(RatingDTO ratingDTO) {
        return null;
    }
}
