package coworking.digitalBooking.Service;
import coworking.digitalBooking.Dto.CoworkingFacilityDTO;

import java.util.List;

public interface CoworkingFacilityService {

    public CoworkingFacilityDTO getCoworkingFacilityById(Long id);

    public List<CoworkingFacilityDTO> getAllCoworkingFacilities();

    public CoworkingFacilityDTO createCoworkingFacility(CoworkingFacilityDTO coworkingFacilityDTO);

    public CoworkingFacilityDTO updateCoworkingFacility(Long id, CoworkingFacilityDTO coworkingFacilityDTO);

    void deleteCoworkingFacility(Long id);
}

