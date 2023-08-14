package coworking.digitalBooking.Dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private long idCategory;
    private String name;
    private String description;
    private String image;
    private Integer results;
}
