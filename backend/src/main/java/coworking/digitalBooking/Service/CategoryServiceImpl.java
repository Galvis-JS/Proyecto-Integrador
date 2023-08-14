package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CategoryDTO;
import coworking.digitalBooking.Entities.Category;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.CategoryRepository;
import coworking.digitalBooking.Repository.CoworkingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CoworkingRepository coworkingRepository;


    @Override
    public CategoryDTO searchById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",id));
        return mapDTO(category);
    }
    @Override
    public List<CategoryDTO> searchAll(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> mapDTO(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO registerCategory(CategoryDTO categoryDTO) {
        Category category = mapEntity(categoryDTO);

        Category newCategory = categoryRepository.save(category);

        CategoryDTO categoryResponse = mapDTO(newCategory);
        return categoryResponse;
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",id));

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setImage(categoryDTO.getImage());
        Category categoryUpdate = categoryRepository.save(category);

        return mapDTO(categoryUpdate);
    }
    @Override
    public void delete(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        if (coworkingRepository.findCoworkingByCategory(category).isEmpty()) {
            categoryRepository.delete(category);
        } else {
            throw new IllegalStateException("Cannot delete category with associated coworkings");
        }
    }


    // Convierte entidad a DTO
    private CategoryDTO mapDTO(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        categoryDTO.setResults(coworkingRepository.findCoworkingByCategory(category).size());
        return categoryDTO;
    }

    // Convierte de DTO a Entidad
    private Category mapEntity(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        return category;
    }
}
