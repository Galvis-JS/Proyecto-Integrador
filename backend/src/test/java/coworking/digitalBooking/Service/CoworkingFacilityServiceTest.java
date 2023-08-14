package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.*;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig
@Transactional
class CoworkingFacilityServiceTest {

    @Autowired
    CoworkingFacilityService coworkingFacilityService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CoworkingService coworkingService;
    @Autowired
    private FacilityService facilityService;

    private CategoryDTO categoryTest;
    private CoworkingFacilityDTO coworkingFacilityDTO;
    private CoworkingDTO coworkingDTO;
    private FacilityDTO facilityDTO;

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

    private FacilityDTO createFacilityDTO(String name, boolean active) {
        FacilityDTO facilityDTO = new FacilityDTO();
        facilityDTO.setName(name);
        facilityDTO.setStatus(active);
        return facilityDTO;
    }

    @BeforeEach
    void prepareData() {
        categoryTest = categoryService.registerCategory(createCategoryDTO("Categoria de prueba para coworking", "Categoria de prueba para coworking", "imagen.jpg", 2));
        CityDTO cityRegister = cityService.registerCity(createCityDTO("Ciudad de prueba para el register", "Ciudad de prueba"));
        coworkingDTO = coworkingService.registerProduct(createCoworkingDTO("coworking para pruebas", "coworking para pruebas", "coworking para pruebas", "img.jpg", 2.443, 2.443, cityRegister, categoryTest, "coworking para pruebas", "coworking para pruebas", "coworking para pruebas"));
        facilityDTO = facilityService.registerFacility(createFacilityDTO("Facility para test", true));
        coworkingFacilityDTO = coworkingFacilityService.createCoworkingFacility(createCoworkingFacilityDTO(coworkingDTO, facilityDTO));
    }

    private CoworkingFacilityDTO createCoworkingFacilityDTO(CoworkingDTO coworkingDTO, FacilityDTO facilityDTO) {
        CoworkingFacilityDTO coworkingFacilityDTO = new CoworkingFacilityDTO();
        coworkingFacilityDTO.setCoworking(coworkingDTO);
        coworkingFacilityDTO.setFacility(facilityDTO);
        return coworkingFacilityDTO;
    }

    @Test
    void getCoworkingFacilityById() {
        assertNotNull(coworkingFacilityService.getCoworkingFacilityById(coworkingFacilityDTO.getId()));
    }

    @Test
    void getAllCoworkingFacilities() {
        assertNotNull(coworkingFacilityService.getAllCoworkingFacilities());
    }

    @Test
    void createCoworkingFacility() {
        CoworkingFacilityDTO coworkingFacilityDTONew = createCoworkingFacilityDTO(coworkingDTO, facilityDTO);
        coworkingFacilityDTONew = coworkingFacilityService.createCoworkingFacility(coworkingFacilityDTONew);
        assertEquals(coworkingFacilityDTONew.getCoworking(), coworkingDTO);
    }

    @Test
    void updateCoworkingFacility() {
        FacilityDTO facilityDTONew = facilityService.registerFacility(createFacilityDTO("Facility 2 para test", true));
        coworkingFacilityDTO.setFacility(facilityDTONew);
        coworkingFacilityDTO = coworkingFacilityService.updateCoworkingFacility(coworkingFacilityDTO.getId(), coworkingFacilityDTO);
        assertEquals(coworkingFacilityDTO.getFacility(), facilityDTONew);
    }

    @Test
    void deleteCoworkingFacility() {
        CoworkingFacilityDTO coworkingFacilityDTONew = createCoworkingFacilityDTO(coworkingDTO, facilityDTO);
        coworkingFacilityDTONew = coworkingFacilityService.createCoworkingFacility(coworkingFacilityDTONew);
        coworkingFacilityService.deleteCoworkingFacility(coworkingFacilityDTONew.getId());
        CoworkingFacilityDTO finalCoworkingFacilityDTONew = coworkingFacilityDTONew;
        assertThrows(ResourceNotFoundException.class, () -> {
            coworkingFacilityService.getCoworkingFacilityById(finalCoworkingFacilityDTONew.getId());
        });
    }
}