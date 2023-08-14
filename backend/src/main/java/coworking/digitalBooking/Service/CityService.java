package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CityDTO;
import java.util.List;


public interface CityService {


    public CityDTO findByName(String name);

    public CityDTO searchById(Long id);

    public List<CityDTO> searchAll();

    public CityDTO registerCity(CityDTO cityDTO);

    public CityDTO update(CityDTO cityDTO, Long id);

    public void delete(Long id);

}
