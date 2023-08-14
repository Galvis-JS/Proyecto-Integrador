package coworking.digitalBooking.Dto;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

    private long idCity;
    private String name;
    private String country;

}
