package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.FavoriteCoworkingDTO;
import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.User;

import java.util.List;

public interface FavoriteCoworkingService {
    public FavoriteCoworkingDTO searchById(Long id);

    public List<FavoriteCoworkingDTO> searchAll();

    public List<FavoriteCoworkingDTO> searchAllByUser(User user);

    public FavoriteCoworkingDTO registerFavoriteCoworking(FavoriteCoworkingDTO favoriteCoworkingDTO);

    public FavoriteCoworkingDTO searchByUserAndCoworking(User user, Coworking coworking);

    void deleteCoworkingFacility(Long id);
}
