package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.UserDTO;
import coworking.digitalBooking.Entities.Rol;
import coworking.digitalBooking.Entities.User;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.RolRepository;
import coworking.digitalBooking.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private EmailService emailService;


    @Override
    public UserDTO searchById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id",id));
        return mapDTO(user);
    }

    @Override
    public List<UserDTO> searchAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id",id));

        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());


        User userUpdate = userRepository.save(user);

        return mapDTO(userUpdate);
    }


    @Override
    public User registerUser(User user) {
        user.setEnabled(false);
        user.setVerificationCode(generateVerificationCode());

        Rol roleUser = rolRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_USER no existe"));

        user.getRoles().add(roleUser);

        return userRepository.save(user);
    }


    @Override
    public void verifyUser(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user != null) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            sendConfirmationEmail(user);
        }
    }

    @Override
    public void sendVerificationEmail(User user) {
        String verificationCode = generateVerificationCode();
        user.setVerificationCode(verificationCode);
        userRepository.save(user);

        String subject = "Verificación de registro";

        try {
            emailService.sendEmailConfirmation(user.getEmail(), subject, verificationCode);
        }catch (MessagingException ex) {
            System.out.println("Error al enviar el correo de registro");
        }
    }

    public void sendConfirmationEmail(User user){
        String subject = "Confirmación de registro";
        String text = "¡Gracias por registrarte! " + "\n"
                    + " Nombre de Usuario: " + user.getName() + "\n"
                    + " Correo Electronico: " + user.getEmail() + "\n"
                    + " Ingresa en el siguiente Link: "
                    + "http://frontend-c3-equipo3.s3-website.us-east-2.amazonaws.com/#/login";

        //emailService.sendEmail(user.getEmail(), subject, text);
        try {
            emailService.sendEmailSuccess(user.getEmail(), subject, user);
        }catch (MessagingException ex) {
            System.out.println("Error al enviar el correo de registro");
        }
    }


    private String generateVerificationCode() {
        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        return code;
    }



    // Convierte entidad a DTO
    private UserDTO mapDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    // Convierte de DTO a Entidad
    private User mapEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }


}
