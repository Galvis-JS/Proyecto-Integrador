package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.CategoryDTO;
import coworking.digitalBooking.Service.CategoryService;
import coworking.digitalBooking.Service.ManageFileS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/Categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ManageFileS3Service manageFilesS3Service;

    @GetMapping()
    public List<CategoryDTO> searchAll() {
        return categoryService.searchAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> searchCategory(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryService.searchById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data", "application/octet-stream"})
    public ResponseEntity<CategoryDTO> registerCategory(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam MultipartFile imageFile
    ) {
        try {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(name);
            categoryDTO.setDescription(description);
            String imageUrl = manageFilesS3Service.uploadFileToS3(imageFile);
            categoryDTO.setImage(imageUrl);
            return new ResponseEntity<>(categoryService.registerCategory(categoryDTO), HttpStatus.CREATED);
        } catch (
                IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO, @PathVariable(name = "id") Long id) {
        CategoryDTO categoryDResponse = categoryService.update(categoryDTO, id);
        return new ResponseEntity<>(categoryDResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            CategoryDTO category = categoryService.searchById(id);
            categoryService.delete(id);
            manageFilesS3Service.deleteFileFromS3(category.getImage());
            return new ResponseEntity<>("Category Delete", HttpStatus.OK);
        } catch (IllegalStateException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

}