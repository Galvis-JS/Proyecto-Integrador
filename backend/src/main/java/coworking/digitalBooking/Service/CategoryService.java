package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public CategoryDTO searchById(Long id);

    public List<CategoryDTO> searchAll();

    public CategoryDTO registerCategory(CategoryDTO categoryDTO);

    public CategoryDTO update(CategoryDTO categoryDTO, Long id);

    public void delete(Long id);
}
