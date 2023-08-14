package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.CityDTO;
import coworking.digitalBooking.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/Cities")
@CrossOrigin(origins = "*")
public class CityController {


    @Autowired
    private CityService cityService;


    @GetMapping()
    public List<CityDTO> searchAll() {
        return cityService.searchAll();
    }

    // Para busqueda de ciudad por id:

    //@GetMapping("/{id}")
    //public ResponseEntity<CityDTO> searchCityId(@PathVariable(name = "id") Long id) {
    //    return ResponseEntity.ok(cityService.searchById(id));
   // }

    @GetMapping("/{name}")
    public ResponseEntity<CityDTO> searchCityName(@PathVariable(name = "name") String name) {
        return ResponseEntity.ok(cityService.findByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<CityDTO> registerCity(@RequestBody CityDTO cityDTO) {
        return new ResponseEntity<>(cityService.registerCity(cityDTO), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> update(@RequestBody CityDTO cityDTO, @PathVariable(name = "id") Long id) {
        CityDTO cityResponse = cityService.update(cityDTO, id);
        return new ResponseEntity<>(cityResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        cityService.delete(id);
        return new ResponseEntity<>("City Delete", HttpStatus.OK);
    }
}
