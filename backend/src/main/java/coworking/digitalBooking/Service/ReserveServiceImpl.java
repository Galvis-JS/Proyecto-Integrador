package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.ReserveDTO;
import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.Reserve;
import coworking.digitalBooking.Entities.User;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.CoworkingRepository;
import coworking.digitalBooking.Repository.ReserveRepository;
import coworking.digitalBooking.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private CoworkingRepository coworkingRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public ReserveDTO searchById(Long id) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserve", "id", id));
        return mapDTO(reserve);
    }

    @Override
    public List<ReserveDTO> searchAll() {
        List<Reserve> reserves = reserveRepository.findAll();
        return reserves.stream().map(reserve -> mapDTO(reserve)).collect(Collectors.toList());
    }

    @Override
    public List<ReserveDTO> searchAllByDate() {
        List<Reserve> reserves = reserveRepository.findAll();
        List<ReserveDTO> reserveDTOs = reserves.stream().map(reserve -> mapDTO(reserve)).collect(Collectors.toList());
        reserveDTOs.sort(Comparator.comparing(ReserveDTO::getStart_date));
        return reserveDTOs;
    }

    @Override
    public List<ReserveDTO> searchAllByCoworking(Coworking coworking) {
        List<Reserve> reserves = reserveRepository.findAllByAttribute(coworking);
        return reserves.stream().map(reserve -> mapDTO(reserve)).collect(Collectors.toList());
    }

    @Override
    public List<ReserveDTO> searchAllByUser(User user) {
        List<Reserve> reserves = reserveRepository.findAllByAttribute(user);
        return reserves.stream().map(reserve -> mapDTO(reserve)).collect(Collectors.toList());
    }

    @Override
    public ReserveDTO registerReserve(ReserveDTO reserveDTO) {
        Reserve reserve = mapEntity(reserveDTO);
        Reserve newReserve = reserveRepository.save(reserve);
        ReserveDTO reserveResponse = mapDTO(newReserve);

        sendConfirmationReserve(reserveDTO);

        return reserveResponse;
    }


    public void sendConfirmationReserve(ReserveDTO reserveDTO){

        Long idUser = reserveDTO.getUser().getId();
        Optional<User> user = userRepository.findById(idUser);

        Long idCow = reserveDTO.getCoworking().getIdCoworking();
        Optional<Coworking> cow = coworkingRepository.findById(idCow);

        String subject = "Confirmación de reserva";
        String text = "¡Datos de tu Reserva! " + "\n\n"
                    + "Coworking Rerservado: " + "\n"
                    + cow.get().getName() + "\n"
                    + "Fecha inicial de tu Reserva: " + "\n"
                    + reserveDTO.getStart_date() + "\n"
                    + "Fecha Final de tu Reserva: " + "\n"
                    + reserveDTO.getEnd_date();

        emailService.sendEmail(user.get().getEmail(), subject, text);
    }


    @Override
    public ReserveDTO update(ReserveDTO reserveDTO, Long id) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Reserve", "id", id));

        reserve.setStart_date(reserveDTO.getStart_date());
        reserve.setEnd_date(reserveDTO.getEnd_date());
        reserve.setCoworking(reserveDTO.getCoworking());

        Reserve reserveUpdate = reserveRepository.save(reserve);
        return mapDTO(reserveUpdate);
    }

    @Override
    public void delete(Long id) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Reserve", "id", id));
        reserveRepository.delete(reserve);
    }

    //buscar disponibilidad de coworkings por fecha
    public List<Coworking> searchCoworkingsAvailableByDates (LocalDateTime startDate, LocalDateTime endDate){
        //obtengo todos los coworkings
        List<Coworking> coworkings = coworkingRepository.findAll();

        //filtro los coworkings por disponibilidad de fechas
        List<Coworking> availableCoworkings = new ArrayList<>();

        for (Coworking coworking : coworkings){
            boolean available = true;

            //verifico si existen reservas que se superpongan con las fechas ingresadas
            for (Reserve reserve : coworking.getReserves()) {
                if (reserve.getStart_date().isBefore(endDate) && reserve.getEnd_date().isAfter(startDate)) {
                    available = false;
                    break;
                }
            }

            if(available){
                availableCoworkings.add(coworking);
            }
        }
        return availableCoworkings;
    }

    public boolean checkCoworkingAvailability(Coworking coworking, LocalDateTime startDate, LocalDateTime endDate){
        List<Reserve> reserves = reserveRepository.findByCoworkingAndDates(coworking, startDate, endDate);
        return reserves.isEmpty();
    }

    public List<LocalDate> getAvailableDates(Coworking coworking, LocalDate initialDate, LocalDate lastDate) {
        List<LocalDate> fechasDisponibles = new ArrayList<>();

        LocalDate currentDate = initialDate;
        while (!currentDate.isAfter(lastDate)) {
            if (checkCoworkingAvailability(coworking, currentDate.atStartOfDay(), currentDate.plusDays(1).atStartOfDay())) {
                fechasDisponibles.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return fechasDisponibles;
    }


    //Convierte entidad a DTO
    private ReserveDTO mapDTO(Reserve reserve) {
        ReserveDTO reserveDTO = modelMapper.map(reserve, ReserveDTO.class);
        return reserveDTO;
    }

    // Convierte de DTO a Entidad
    private Reserve mapEntity(ReserveDTO reserveDTO) {
        Reserve reserve = modelMapper.map(reserveDTO, Reserve.class);
        return reserve;
    }
}
