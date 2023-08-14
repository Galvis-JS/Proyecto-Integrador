package coworking.digitalBooking.Service;

import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Dto.FavoriteCoworkingDTO;
import coworking.digitalBooking.Entities.FavoriteCoworking;
import coworking.digitalBooking.Entities.User;
import coworking.digitalBooking.Repository.FavoriteCoworkingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteCoworkingImplement implements FavoriteCoworkingService{

    private final FavoriteCoworkingRepository favoriteCoworkingRepository;
    private final ModelMapper modelMapper;

    public FavoriteCoworkingImplement(FavoriteCoworkingRepository favoriteCoworkingRepository, ModelMapper modelMapper) {
        this.favoriteCoworkingRepository = favoriteCoworkingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FavoriteCoworkingDTO searchById(Long id) {
        FavoriteCoworking favoriteCoworking = favoriteCoworkingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking", "id",id));
        return mapDTO(favoriteCoworking);
    }

    @Override
    public List<FavoriteCoworkingDTO> searchAll() {
        List<FavoriteCoworking> favoriteCoworkings = favoriteCoworkingRepository.findAll();
        return favoriteCoworkings.stream().map(favoriteCoworking -> mapDTO(favoriteCoworking))
                .collect(Collectors.toList());
    }

    @Override
    public List<FavoriteCoworkingDTO> searchAllByUser(User user) {
        List<FavoriteCoworking> favoriteCoworkings = favoriteCoworkingRepository.findFavoriteCoworkingByUser(user);
        return favoriteCoworkings.stream().map(favoriteCoworking -> mapDTO(favoriteCoworking))
                .collect(Collectors.toList());
    }

    @Override
    public FavoriteCoworkingDTO registerFavoriteCoworking(FavoriteCoworkingDTO favoriteCoworkingDTO) {
        FavoriteCoworking favoriteCoworking = mapEntity(favoriteCoworkingDTO);
        FavoriteCoworking newFavorite = favoriteCoworkingRepository.save(favoriteCoworking);
        FavoriteCoworkingDTO favoriteResponse = mapDTO(newFavorite);
        return favoriteResponse;
    }

    public FavoriteCoworkingDTO searchByUserAndCoworking(User user, Coworking coworking) {
        FavoriteCoworking favoriteCoworking = favoriteCoworkingRepository.findFavoriteCoworkingByUserAndCoworking(user, coworking);

        if(favoriteCoworking != null){
            return mapDTO(favoriteCoworking);
        }else{
            return null;
        }
    }

    @Override
    public void deleteCoworkingFacility(Long id) {
        FavoriteCoworking favoriteCoworking = favoriteCoworkingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FavoriteCoworking", "id", id));
        favoriteCoworkingRepository.delete(favoriteCoworking);
    }

    // Convierte entidad a DTO
    private FavoriteCoworkingDTO mapDTO(FavoriteCoworking favoriteCoworking) {
        FavoriteCoworkingDTO favoriteCoworkingDTO = modelMapper.map(favoriteCoworking, FavoriteCoworkingDTO.class);
        favoriteCoworkingDTO.getCoworking().setFavoriteCoworkingId(favoriteCoworkingDTO.getId());
        return favoriteCoworkingDTO;
    }

    // Convierte de DTO a Entidad
    private FavoriteCoworking mapEntity(FavoriteCoworkingDTO favoriteCoworkingDTO) {
        FavoriteCoworking favoriteCoworking = modelMapper.map(favoriteCoworkingDTO, FavoriteCoworking.class);
        return favoriteCoworking;
    }
}
