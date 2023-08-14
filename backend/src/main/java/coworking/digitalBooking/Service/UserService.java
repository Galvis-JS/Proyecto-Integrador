package coworking.digitalBooking.Service;


import coworking.digitalBooking.Dto.UserDTO;
import coworking.digitalBooking.Entities.User;

import java.util.List;

public interface UserService {


    public UserDTO searchById(Long id);

    public List<UserDTO> searchAll();

    public UserDTO update(UserDTO userDTO, Long id);

    public User registerUser(User user);

    public void verifyUser(String verificationCode);

    public void sendVerificationEmail(User user);

}
