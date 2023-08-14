package coworking.digitalBooking.Controller;

import coworking.digitalBooking.Dto.UserDTO;
import coworking.digitalBooking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserDTO> searchAll() {
        return userService.searchAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> searchUser(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.searchById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable(name = "id") Long id) {
        UserDTO userResponse = userService.update(userDTO, id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


}
