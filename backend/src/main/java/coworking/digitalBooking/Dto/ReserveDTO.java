package coworking.digitalBooking.Dto;

import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDTO {
    private long idReserve;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Coworking coworking;
    private User user;
}
