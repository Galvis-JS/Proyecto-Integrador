package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.FacilityDTO;

import java.util.List;


public interface FacilityService {

    public FacilityDTO findByName(String name);

    public FacilityDTO searchById(Long id);

    public List<FacilityDTO> searchAll();

    public FacilityDTO registerFacility(FacilityDTO facilityDTO);

    public FacilityDTO update(FacilityDTO facilityDTO, Long id);

    public void delete(Long id);

}
