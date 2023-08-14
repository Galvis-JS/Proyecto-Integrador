package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.*;
import coworking.digitalBooking.Entities.User;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig
@Transactional
class FavoriteCoworkingServiceTest {

    @Autowired
    private CityService cityService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CoworkingService coworkingService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteCoworkingService favoriteCoworkingService;

    private CategoryDTO categoryTest;
    private FavoriteCoworkingDTO favoriteCoworkingDTO;
    private CoworkingDTO coworkingDTO;
    private User user;

    private UserDTO userDTO;

    private CategoryDTO createCategoryDTO(String name, String description, String image, int results) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        categoryDTO.setImage(image);
        categoryDTO.setResults(results);
        return categoryDTO;
    }

    private CityDTO createCityDTO(String name, String country) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName(name);
        cityDTO.setCountry(country);
        return cityDTO;
    }

    private CoworkingDTO createCoworkingDTO(String name, String description, String address, String image, double latitude, double longitude, CityDTO cityDTO, CategoryDTO categoryDTO, String coworkingRulesPolicy, String cancellationPolicy, String healthSafetyPolicy) {
        CoworkingDTO coworkingDTO = new CoworkingDTO();
        coworkingDTO.setName(name);
        coworkingDTO.setDescription(description);
        coworkingDTO.setAddress(address);
        coworkingDTO.setImage(image);
        coworkingDTO.setLatitude(latitude);
        coworkingDTO.setLongitude(longitude);
        coworkingDTO.setCity(cityDTO);
        coworkingDTO.setCategory(categoryDTO);
        coworkingDTO.setCoworkingRulesPolicy(coworkingRulesPolicy);
        coworkingDTO.setCancellationPolicy(cancellationPolicy);
        coworkingDTO.setHealthSafetyPolicy(healthSafetyPolicy);
        return coworkingDTO;
    }

    private User createUser(String name, String lastName, String email, String password){
        User user = new User();
        user.setName(name);
        user.setLastname(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    private FavoriteCoworkingDTO createCoworkingFacilityDTO(CoworkingDTO coworkingDTO, UserDTO userDTO){
        FavoriteCoworkingDTO favoriteCoworkingDTO = new FavoriteCoworkingDTO();
        favoriteCoworkingDTO.setCoworking(coworkingDTO);
        favoriteCoworkingDTO.setUser(userDTO);
        return favoriteCoworkingDTO;
    }

    private UserDTO createUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    @BeforeEach
    void prepareData() {
        categoryTest = categoryService.registerCategory(createCategoryDTO("Categoria de prueba para coworking favorito", "Categoria de prueba para coworking", "imagen.jpg", 2));
        CityDTO cityRegister = cityService.registerCity(createCityDTO("Ciudad de prueba para el register", "Ciudad de prueba"));
        coworkingDTO = coworkingService.registerProduct(createCoworkingDTO("coworking para pruebas", "coworking para pruebas", "coworking para pruebas", "img.jpg", 2.443, 2.443, cityRegister, categoryTest, "coworking para pruebas", "coworking para pruebas", "coworking para pruebas"));
        user = userRepository.save(createUser("test-unit", "test-unit","test-unit@hotmail.com","test-unit"));
        userDTO = createUserDTO(user);
        favoriteCoworkingDTO = favoriteCoworkingService.registerFavoriteCoworking(createCoworkingFacilityDTO(coworkingDTO, userDTO));
    }

    @Test
    void searchById() {
        assertNotNull(favoriteCoworkingService.searchById(favoriteCoworkingDTO.getId()));
    }

    @Test
    void searchAll() {
        assertNotNull(favoriteCoworkingService.searchAll());
    }

    @Test
    void searchAllByUser() {
        assertNotNull(favoriteCoworkingService.searchAllByUser(user));
    }

    @Test
    void registerFavoriteCoworking() {

        CityDTO cityRegister = cityService.registerCity(createCityDTO("Ciudad de prueba para el register favorito", "Ciudad de prueba"));
        CoworkingDTO coworkingDTO1 = coworkingService.registerProduct(createCoworkingDTO("coworking para pruebas favorito register", "coworking para pruebas", "coworking para pruebas", "img.jpg", 2.443, 2.443, cityRegister, categoryTest, "coworking para pruebas", "coworking para pruebas", "coworking para pruebas"));
        FavoriteCoworkingDTO favoriteCoworkingDTO1 = favoriteCoworkingService.registerFavoriteCoworking(createCoworkingFacilityDTO(coworkingDTO1, userDTO));
        assertEquals(coworkingDTO1.getIdCoworking(), favoriteCoworkingDTO1.getCoworking().getIdCoworking());
    }

    @Test
    void deleteCoworkingFacility() {
        CityDTO cityRegister = cityService.registerCity(createCityDTO("Ciudad de prueba para el eliminar favorito", "Ciudad de prueba"));
        CoworkingDTO coworkingDTO1 = coworkingService.registerProduct(createCoworkingDTO("coworking para pruebas favorito eliminar", "coworking para pruebas", "coworking para pruebas", "img.jpg", 2.443, 2.443, cityRegister, categoryTest, "coworking para pruebas", "coworking para pruebas", "coworking para pruebas"));
        FavoriteCoworkingDTO favoriteCoworkingDTO1 = favoriteCoworkingService.registerFavoriteCoworking(createCoworkingFacilityDTO(coworkingDTO1, userDTO));
        favoriteCoworkingService.deleteCoworkingFacility(favoriteCoworkingDTO1.getId());
        assertThrows(ResourceNotFoundException.class, () -> {
            favoriteCoworkingService.searchById(favoriteCoworkingDTO1.getId());
        });

    }
}