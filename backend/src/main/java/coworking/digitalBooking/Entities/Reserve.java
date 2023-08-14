package coworking.digitalBooking.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Reserves")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReserve;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime start_date;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime end_date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coworking")
    private Coworking coworking;
    /*en Coworking tengo que declarar:
    @OneToMany(mappedBy = "coworking", cascade = CascadeType.ALL)
    private List<Reserve> reserves;

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void addReserva(Reserva reserva) {
        if (reservas == null) {
            reservas = new ArrayList<>();
        }
        reservas.add(reserva);
        reserva.setCoworking(this);
    }

    public void removeReserva(Reserva reserva) {
        if (reservas != null) {
            reservas.remove(reserva);
            reserva.setCoworking(null);
        }
     */

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
