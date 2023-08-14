package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.FacilityDTO;
import coworking.digitalBooking.Service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Facilities")
@CrossOrigin(origins = "*")
public class FacilityController {


    @Autowired
    private FacilityService facilityService;


    @GetMapping()
    public List<FacilityDTO> searchAll() {
        return facilityService.searchAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<FacilityDTO> searchFacilityName(@PathVariable(name = "name") String name) {
        return ResponseEntity.ok(facilityService.findByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<FacilityDTO> registerFacility(@RequestBody FacilityDTO facilityDTO) {
        return new ResponseEntity<>(facilityService.registerFacility(facilityDTO), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FacilityDTO> update(@RequestBody FacilityDTO facilityDTO, @PathVariable(name = "id") Long id) {
        FacilityDTO facilityResponse = facilityService.update(facilityDTO, id);
        return new ResponseEntity<>(facilityResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        facilityService.delete(id);
        return new ResponseEntity<>("Facility Delete", HttpStatus.OK);
    }
}
