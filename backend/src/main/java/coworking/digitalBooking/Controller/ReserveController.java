package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.ReserveDTO;
import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.User;
import coworking.digitalBooking.Service.CoworkingService;
import coworking.digitalBooking.Service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/Reserves")
@CrossOrigin(origins = "*")
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    @Autowired
    private CoworkingService coworkingService;

    //obtener una reserva especifica con el id de la reserva
    @GetMapping("/{id}")
    public ResponseEntity<ReserveDTO> searchById (@PathVariable(name="id") Long id){
        return ResponseEntity.ok(reserveService.searchById(id));
    }

    //obtener todas las reservas (de todos los coworkings)
    @GetMapping
    public List<ReserveDTO> searchAll (){
        return reserveService.searchAll();
    }

    @GetMapping ("/getAllByDate")
    public List<ReserveDTO> searchAllByDate (){
        return reserveService.searchAllByDate();
    }

    //obtener todas las reservas de un coworking specific con el id del coworking
    @GetMapping("/coworking")
    public List<ReserveDTO> searchAllByCoworking(
            @RequestParam("coworking") Coworking coworking){
        return reserveService.searchAllByCoworking(coworking);
    }

    //obtener todas las reservas de un usuario especifico con el id del usuario
    @GetMapping("/user")
    public List<ReserveDTO> searchAllByUser(
            @RequestParam("user") User user){
        return reserveService.searchAllByUser(user);
    }

    //registrar una nueva reserva
    @PostMapping()
    public ResponseEntity<ReserveDTO> registerReserve(@RequestBody ReserveDTO reserveDTO) {
        return new ResponseEntity<>(reserveService.registerReserve(reserveDTO), HttpStatus.CREATED);
    }

    //editar una reserva
    @PutMapping("/{id}")
    public ResponseEntity<ReserveDTO> update (@RequestBody ReserveDTO reserveDTO, @PathVariable (name = "id") Long id){
        ReserveDTO reserveResponse = reserveService.update(reserveDTO, id);
        return new ResponseEntity<>(reserveResponse, HttpStatus.OK);
    }

    //eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable(name = "id") Long id){
        reserveService.delete(id);
        return new ResponseEntity<>("Reserve Deleted", HttpStatus.OK);
    }

    //obtener todos los coworkings disponibles entre un rango de fecha
    @GetMapping("/availability")
    public ResponseEntity<List<Coworking>> searchCoworkingsAvailableByDates (
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Coworking> availability = reserveService.searchCoworkingsAvailableByDates(startDate, endDate);
        return ResponseEntity.ok(availability);
    }

    //saber si un coworking especifico esta disponible entre un rango de fechas dado
    @GetMapping("/isAvailable")
    public ResponseEntity<Boolean> checkCoworkingAvailability (
            @RequestParam("coworking") Coworking coworking,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Boolean isAvailable = reserveService.checkCoworkingAvailability(coworking, startDate, endDate);
        return ResponseEntity.ok(isAvailable);
    }

    //obtener todas las fechas disponibles de un coworking especifico dado un rango de fecha
    @GetMapping("/datesAvailable")
    public ResponseEntity<List<LocalDate>> getAvailableDates (
            @RequestParam("coworking") Coworking coworking,
            @RequestParam("initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate initialDate,
            @RequestParam("lastDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate lastDate){
        List<LocalDate> datesAvailable = reserveService.getAvailableDates(coworking, initialDate, lastDate);
        return ResponseEntity.ok(datesAvailable);
    }


}
