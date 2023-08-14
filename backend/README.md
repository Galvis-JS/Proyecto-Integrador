# Digital Booking 
## Backend 
## Unit Tests
Los tests unitarios en el área de backend se realizaron utilizando el framework Junit. Los tests se realizaron en los siguientes servicios o *services*: Category, City, Coworking y CoworkingFacility. 

### Test unitario: delete() - CategoryService

```
package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CategoryDTO;
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
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    private CategoryDTO categoryTest;

    private CategoryDTO createCategoryDTO(String name, String description, String image, int results) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        categoryDTO.setImage(image);
        categoryDTO.setResults(results);
        return categoryDTO;
    }

    @BeforeEach
    void prepareData() {
        CategoryDTO categoryDTO = createCategoryDTO("Categoria de prueba para eliminar", "Categoria de prueba para eliminar", "imagen.jpg", 2);
        categoryTest = categoryService.registerCategory(categoryDTO);
    }

    @Test
    @Order(1)
    void registerCategory() {
        CategoryDTO categoryDTO = createCategoryDTO("Categoria de prueba del insert", "Categoria de prueba", "imagen.jpg", 2);

        CategoryDTO categoryTestRegister = categoryService.registerCategory(categoryDTO);
        assertEquals(categoryDTO.getName(), categoryTestRegister.getName());
        assertNotNull(categoryService.searchById(categoryTestRegister.getIdCategory()));
    }

    @Test
    @Order(2)
    void delete() {
        assertNotNull(categoryTest);
        categoryService.delete(categoryTest.getIdCategory());
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.searchById(categoryTest.getIdCategory());
        });
    }
}
```

### Test unitario: CityService - CityServiceTest

```
package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CityDTO;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@SpringJUnitConfig
@Transactional
class CityServiceTest {

    @Autowired
    private CityService cityService;

    private CityDTO cityDTOTest;

    @BeforeEach
    void prepareData() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Ciudad de prueba");
        cityDTO.setCountry("Ciudad de prueba");
        cityDTOTest = cityService.registerCity(cityDTO);
    }

    @Test
    void findByName() {
        assertNotNull(cityService.findByName(cityDTOTest.getName()));
    }

    @Test
    void searchById() {
        assertNotNull(cityService.searchById(cityDTOTest.getIdCity()));
    }

    @Test
    void searchAll() {
        assertNotNull(cityService.searchAll());
    }

    @Test
    void registerCity() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Ciudad de prueba para el register");
        cityDTO.setCountry("Ciudad de prueba");
        CityDTO cityRegister = cityService.registerCity(cityDTO);

        assertEquals(cityRegister.getName(), cityDTO.getName());
    }

    @Test
    void update() {
        String countryNew = "nuevo pais";
        cityDTOTest.setCountry(countryNew);
        cityDTOTest = cityService.update(cityDTOTest, cityDTOTest.getIdCity());
        assertEquals(cityDTOTest.getCountry(), countryNew);
    }

    @Test
    void delete() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Ciudad de prueba para el register");
        cityDTO.setCountry("Ciudad de prueba");
        CityDTO cityRegister = cityService.registerCity(cityDTO);
        cityService.delete(cityRegister.getIdCity());
        assertThrows(ResourceNotFoundException.class, () -> {
            cityService.searchById(cityRegister.getIdCity());
        });
    }
}
```

### Test unitario: CoworkingService - CoworkingServiceTest

```
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
```

### Test unitario: CoworkingFacilityService - CoworkingFacilityServiceTest

```
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
    void getAllCoworkingPolicies() {
        assertNotNull(coworkingFacilityService.getAllCoworkingPolicies());
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
```