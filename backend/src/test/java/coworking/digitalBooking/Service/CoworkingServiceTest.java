package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.*;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
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
class CoworkingServiceTest {

    @Autowired
    private CityService cityService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CoworkingService coworkingService;
    private CategoryDTO categoryTest;
    private CoworkingDTO coworkingDTO;
    private  CityDTO cityRegister;

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

    @BeforeEach
    void prepareData() {
        categoryTest = categoryService.registerCategory(createCategoryDTO("Categoria de prueba para coworking", "Categoria de prueba para coworking", "imagen.jpg", 2));
        cityRegister = cityService.registerCity(createCityDTO("Ciudad de prueba para el register", "Ciudad de prueba"));
        coworkingDTO = coworkingService.registerProduct(createCoworkingDTO("coworking para pruebas", "coworking para pruebas", "coworking para pruebas", "img.jpg", 2.443, 2.443, cityRegister, categoryTest, "coworking para pruebas", "coworking para pruebas", "coworking para pruebas"));
    }

    @Test
    void searchById() {
        assertNotNull(coworkingService.searchById(coworkingDTO.getIdCoworking()));
    }

    @Test
    void searchAll() {
        assertNotNull(coworkingService.searchAll());
    }

    @Test
    void registerProduct() {
        String name = "coworking para pruebas 2";
        CoworkingDTO coworkingDTOTest = coworkingService.registerProduct(createCoworkingDTO(name, name, "coworking para pruebas", "img.jpg", 2.443, 2.443, cityRegister, categoryTest, "coworking para pruebas", "coworking para pruebas", "coworking para pruebas"));
        assertNotNull(coworkingDTOTest);
    }

    @Test
    void update() {
        String name = "coworking para pruebas del update";
        coworkingDTO.setName(name);
        coworkingDTO = coworkingService.update(coworkingDTO, coworkingDTO.getIdCoworking());
        assertEquals(name, coworkingDTO.getName());
    }

    @Test
    void delete() {
        String name = "coworking para pruebas del delete";
        CoworkingDTO coworkingDTOTest = coworkingService.registerProduct(createCoworkingDTO(name, name, "coworking para pruebas", "img.jpg", 2.443, 2.443, cityRegister, categoryTest, "coworking para pruebas", "coworking para pruebas", "coworking para pruebas"));

        assertNotNull(coworkingDTOTest);
        coworkingService.delete(coworkingDTOTest.getIdCoworking());

        CoworkingDTO finalCoworkingDTOTest = coworkingDTOTest;
        assertThrows(ResourceNotFoundException.class, () -> {
            coworkingService.searchById(finalCoworkingDTOTest.getIdCoworking());
        });
    }
}