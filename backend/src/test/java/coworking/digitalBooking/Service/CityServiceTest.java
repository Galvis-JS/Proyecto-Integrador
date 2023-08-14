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