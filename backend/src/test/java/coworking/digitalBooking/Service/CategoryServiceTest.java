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

    private CategoryDTO createCategoryDTO(String name, String description, String image) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        categoryDTO.setImage(image);
        return categoryDTO;
    }

    @BeforeEach
    void prepareData() {
        CategoryDTO categoryDTO = createCategoryDTO("Categoria de prueba para eliminar", "Categoria de prueba para eliminar", "imagen.jpg");
        categoryTest = categoryService.registerCategory(categoryDTO);
    }

    @Test
    @Order(1)
    void registerCategory() {
        CategoryDTO categoryDTO = createCategoryDTO("Categoria de prueba del insert", "Categoria de prueba", "imagen.jpg");

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