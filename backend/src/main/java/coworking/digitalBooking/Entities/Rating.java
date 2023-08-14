package coworking.digitalBooking.Entities;


import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Long idCoworking;
    @Column
    private Integer puntuacion;

    public void setPuntuacion(Integer puntuacion) {
        if(puntuacion >= 0 && puntuacion <= 5){
            this.puntuacion = puntuacion;
        }else{
            System.out.println("La puntuacion debe estar entre 0 y 5");
        }
    }

    
}
