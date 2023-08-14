package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.ReserveDTO;
import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReserveService {

    public ReserveDTO searchById(Long id);

    public List<ReserveDTO> searchAll();

    public List<ReserveDTO> searchAllByDate();

    public List<ReserveDTO> searchAllByCoworking(Coworking coworking);

    public List<ReserveDTO> searchAllByUser(User user);

    public ReserveDTO registerReserve (ReserveDTO reserveDTO);

    public ReserveDTO update(ReserveDTO reserveDTO, Long id);

    public void delete(Long id);

    public List<Coworking> searchCoworkingsAvailableByDates (LocalDateTime startDate, LocalDateTime endDate);

    public boolean checkCoworkingAvailability(Coworking coworking, LocalDateTime startDate, LocalDateTime endDate);

    public List<LocalDate> getAvailableDates(Coworking coworking, LocalDate initialDate, LocalDate lastDate);

}
