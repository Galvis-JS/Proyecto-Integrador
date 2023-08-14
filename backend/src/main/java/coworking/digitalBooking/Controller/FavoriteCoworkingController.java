package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.CoworkingDTO;
import coworking.digitalBooking.Dto.FavoriteCoworkingDTO;
import coworking.digitalBooking.Dto.UserDTO;
import coworking.digitalBooking.Entities.User;
import coworking.digitalBooking.Repository.UserRepository;
import coworking.digitalBooking.Service.CoworkingService;
import coworking.digitalBooking.Service.FavoriteCoworkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Favorites")
@CrossOrigin(origins = "*")
public class FavoriteCoworkingController {


    @Autowired
    private FavoriteCoworkingService favoriteCoworkingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoworkingService coworkingService;


    @GetMapping()
    public List<FavoriteCoworkingDTO> searchAll() {
        return favoriteCoworkingService.searchAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<FavoriteCoworkingDTO> searchCategoryId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(favoriteCoworkingService.searchById(id));
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public List<FavoriteCoworkingDTO> searchByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional= userRepository.findByEmail(authentication.getName());
        return favoriteCoworkingService.searchAllByUser(userOptional.get());
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/coworkings")
    public List<CoworkingDTO> searchCoworkingByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userRepository.findByEmail(authentication.getName());

        return favoriteCoworkingService.searchAllByUser(userOptional.get())
                .stream()
                .map(FavoriteCoworkingDTO::getCoworking)
                .collect(Collectors.toList());
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<?> registerFavorite(@RequestBody CoworkingDTO coworkingDTO) {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> userOptional= userRepository.findByEmail(authentication.getName());

            coworkingDTO = coworkingService.searchById(coworkingDTO.getIdCoworking());

            UserDTO userDTO = new UserDTO();
            userDTO.setId(userOptional.get().getId());

            FavoriteCoworkingDTO favoriteCoworkingDTO = new FavoriteCoworkingDTO();
            favoriteCoworkingDTO.setCoworking(coworkingDTO);
            favoriteCoworkingDTO.setUser(userDTO);

            return new ResponseEntity<>(favoriteCoworkingService.registerFavoriteCoworking(favoriteCoworkingDTO), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        favoriteCoworkingService.deleteCoworkingFacility(id);
        return new ResponseEntity<>("Favorite Delete", HttpStatus.OK);
    }
}
