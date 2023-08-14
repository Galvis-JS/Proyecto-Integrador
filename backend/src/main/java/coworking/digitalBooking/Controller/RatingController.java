
package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.CategoryDTO;
import coworking.digitalBooking.Dto.RatingDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import coworking.digitalBooking.Entities.Rating;
import coworking.digitalBooking.Service.RatingServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/Ratings")
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    private RatingServiceImplement ratingService;

    @PostMapping(consumes = {"multipart/form-data", "application/octet-stream"})
    public ResponseEntity<Rating> registerRating(
            @RequestParam Long idCoworking,
            @RequestParam Integer puntuacion
    ) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setidCoworking(idCoworking);
        ratingDTO.setpuntuacion(puntuacion);
        return ResponseEntity.ok(ratingService.register(idCoworking, puntuacion));
    }
}
