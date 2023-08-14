package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CoworkingDTO;
import coworking.digitalBooking.Dto.FavoriteCoworkingDTO;
import coworking.digitalBooking.Entities.Category;
import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.User;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.CategoryRepository;
import coworking.digitalBooking.Repository.CoworkingRepository;
import coworking.digitalBooking.Repository.RatingRepository;
import coworking.digitalBooking.Repository.RatingRepository.RatingResult;
import coworking.digitalBooking.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoworkingServiceImple implements CoworkingService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoworkingRepository coworkingRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private FavoriteCoworkingService favoriteCoworkingService;

    private User user = null;

    private void findUserAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            Optional<User> userOptional= userRepository.findByEmail(authentication.getName());
            if(userOptional.isPresent()){
                this.user = userOptional.get();
            }
        }
    }

    @Override
    public CoworkingDTO searchById(Long id){
        findUserAuthentication();
        Coworking coworking = coworkingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking", "id",id));
        return mapDTO(coworking);
    }

    @Override
    public List<CoworkingDTO> searchByCategoryId(long categoryId) {
        findUserAuthentication();
        Category category = categoryRepository.findByIdCategory(categoryId);
        List<Coworking> coworkings = coworkingRepository.findCoworkingByCategory(category);
        return coworkings.stream().map(coworking -> mapDTO(coworking)).collect(Collectors.toList());
    }

    @Override
    public List<CoworkingDTO> searchAll(){
        findUserAuthentication();
        List<Coworking> coworkings = coworkingRepository.findAll();
        return coworkings.stream().map(coworking -> mapDTO(coworking)).collect(Collectors.toList());
    }



    @Override
    public CoworkingDTO registerProduct(CoworkingDTO coworkingDTO) {
        Coworking coworkingDuplicate = coworkingRepository.findByName(coworkingDTO.getName());
        if (coworkingDuplicate != null){
            throw new ResourceNotFoundException("Coworking", "id 0 ya que no se ha creado debido a que ya existe un coworking con ese Nombre ", 0);
        }
        Category category = categoryRepository.findByIdCategory(coworkingDTO.getCategory().getIdCategory());
        if (category == null) {
            throw new ResourceNotFoundException("Category", "id",coworkingDTO.getCategory().getIdCategory());
        }

        Coworking coworking = mapEntity(coworkingDTO);
        Coworking newCoworking = coworkingRepository.save(coworking);
        CoworkingDTO coworkingResponse = mapDTO(newCoworking);
        return coworkingResponse;
    }

    @Override
    public CoworkingDTO update(CoworkingDTO coworkingDTO, Long id) {

        Coworking coworkingUpdate= mapEntity(coworkingDTO);
        Coworking coworking = coworkingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking", "id",id));

        coworking.setCity(coworkingUpdate.getCity());
        coworking.setCategory(coworkingUpdate.getCategory());
        coworking.setImage(coworking.getImage());
        coworking.setName(coworkingDTO.getName());
        coworking.setAddress(coworkingDTO.getAddress());
        coworking.setDescription(coworkingDTO.getDescription());
        coworking.setCancellationPolicy(coworkingDTO.getCancellationPolicy());
        coworking.setCoworkingRulesPolicy(coworkingDTO.getCoworkingRulesPolicy());
        coworking.setHealthSafetyPolicy(coworkingDTO.getHealthSafetyPolicy());
        coworking.setLatitude(coworkingDTO.getLatitude());
        coworking.setLongitude(coworkingDTO.getLongitude());

        return mapDTO(coworkingRepository.save(coworking));
    }


    public void delete(Long id){
        Coworking coworking = coworkingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coworking", "id",id));
        coworkingRepository.delete(coworking);
    }

    // Convierte entidad a DTO
    private CoworkingDTO mapDTO(Coworking coworking) {
        CoworkingDTO coworkingDTO = modelMapper.map(coworking, CoworkingDTO.class);
        RatingResult ratingResult = ratingRepository.getRatingByIdCoworking(coworking.getIdCoworking());
        if(this.user != null){
            FavoriteCoworkingDTO favoriteCoworkingDTO = favoriteCoworkingService.searchByUserAndCoworking(this.user, coworking);
            coworkingDTO.setFavoriteCoworkingId(favoriteCoworkingDTO != null ? favoriteCoworkingDTO.getId() : null );
        }

        coworkingDTO.setRating(ratingResult);
        return coworkingDTO;
    }

    // Convierte de DTO a Entidad
    private Coworking mapEntity(CoworkingDTO coworkingDTO) {
        Coworking coworking = modelMapper.map(coworkingDTO, Coworking.class);
        return coworking;
    }



    public void validateCoordinates(double latitud, double longitud) {
        if (!validLatitude(latitud)) {
            throw new IllegalArgumentException("Latitud inválida, debe estar entre -90 y 90: " + latitud);
        }
        if (!validLongitude(longitud)) {
            throw new IllegalArgumentException("Longitud inválida, debe estar entre -180 y 180: " + longitud);
        }
    }

    private static boolean validLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    private static boolean validLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

}
