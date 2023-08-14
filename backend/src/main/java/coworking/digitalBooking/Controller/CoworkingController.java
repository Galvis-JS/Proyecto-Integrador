package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.*;
import coworking.digitalBooking.Service.CoworkingFacilityService;
import coworking.digitalBooking.Service.CoworkingService;
import coworking.digitalBooking.Service.ManageFileS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Products")
@CrossOrigin(origins = "*")
public class CoworkingController {
    @Autowired
    private CoworkingService coworkingService;
    @Autowired
    private ManageFileS3Service manageFilesS3Service;
    @Autowired
    private CoworkingFacilityService coworkingFacilityService;

    @GetMapping()
    public List<CoworkingDTO> searchAll() {
        return coworkingService.searchAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoworkingDTO> searchProduct(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(coworkingService.searchById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data", "application/octet-stream"})
    public ResponseEntity<CoworkingDTO> registerProduct(
            @RequestParam String name,
            @RequestParam Long category,
            @RequestParam Long city,
            @RequestParam String address,
            @RequestParam String description,
            @RequestParam MultipartFile imageFile1,
            @RequestParam MultipartFile imageFile2,
            @RequestParam MultipartFile imageFile3,
            @RequestParam MultipartFile imageFile4,
            @RequestParam MultipartFile imageFile5,
            @RequestParam String coworking_rules_policy,
            @RequestParam String health_safety_policy,
            @RequestParam String cancellation_policy,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam List<Long> facilities
    ) {
        try {

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setIdCategory(category);

            CityDTO cityDTO = new CityDTO();
            cityDTO.setIdCity(city);

            CoworkingDTO coworkingDTO = new CoworkingDTO();
            coworkingDTO.setName(name);
            coworkingDTO.setCategory(categoryDTO);
            coworkingDTO.setCity(cityDTO);
            coworkingDTO.setAddress(address);
            coworkingDTO.setDescription(description);
            coworkingDTO.setCoworkingRulesPolicy(coworking_rules_policy);
            coworkingDTO.setHealthSafetyPolicy(health_safety_policy);
            coworkingDTO.setCancellationPolicy(cancellation_policy);
            coworkingDTO.setLatitude(latitude);
            coworkingDTO.setLongitude(longitude);

            String imageUrl1 = manageFilesS3Service.uploadFileToS3(imageFile1);
            String imageUrl2 = manageFilesS3Service.uploadFileToS3(imageFile2);
            String imageUrl3 = manageFilesS3Service.uploadFileToS3(imageFile3);
            String imageUrl4 = manageFilesS3Service.uploadFileToS3(imageFile4);
            String imageUrl5 = manageFilesS3Service.uploadFileToS3(imageFile5);


            coworkingDTO.setImage(imageUrl1 + ";" + imageUrl2 + ";" + imageUrl3+ ";" + imageUrl4 + ";" + imageUrl5);
            coworkingService.validateCoordinates(latitude,longitude);
            coworkingDTO = coworkingService.registerProduct(coworkingDTO);

            registerFacilities(facilities, coworkingDTO);

            return new ResponseEntity<>(coworkingDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void registerFacilities(List<Long> facilities, CoworkingDTO coworkingDTO){
        for (Long facility : facilities) {
            CoworkingFacilityDTO coworkingFacilityDTO = new CoworkingFacilityDTO();
            FacilityDTO facilityDTO = new FacilityDTO();
            facilityDTO.setId(facility);
            coworkingFacilityDTO.setCoworking(coworkingDTO);
            coworkingFacilityDTO.setFacility(facilityDTO);
            coworkingFacilityService.createCoworkingFacility(coworkingFacilityDTO);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CoworkingDTO> update(@RequestBody CoworkingDTO coworkingDTO, @PathVariable(name = "id") Long id) {

        CoworkingDTO coworkingResponse = coworkingService.update(coworkingDTO, id);

        return new ResponseEntity<>(coworkingResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {

        CoworkingDTO coworkingDTO = coworkingService.searchById(id);
        coworkingService.delete(id);
        String[] imageUrls = coworkingDTO.getImage().split(";");
        for (String imageUrl : imageUrls) {
            manageFilesS3Service.deleteFileFromS3(imageUrl.trim());
        }
        return new ResponseEntity<>("Coworking Delete", HttpStatus.OK);
    }
}