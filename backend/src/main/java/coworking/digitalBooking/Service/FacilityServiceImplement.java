package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.FacilityDTO;
import coworking.digitalBooking.Entities.Facility;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.FacilityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityServiceImplement implements FacilityService{


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public FacilityDTO findByName(String name) {
        Facility facility = facilityRepository.findByName(name);
        return mapDTO(facility);
    }

    @Override
    public FacilityDTO searchById(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility", "id",id));
        return mapDTO(facility);
    }

    @Override
    public List<FacilityDTO> searchAll() {
        List<Facility> cities = facilityRepository.findAll();
        return cities.stream().map(facility -> mapDTO(facility)).collect(Collectors.toList());
    }

    @Override
    public FacilityDTO registerFacility(FacilityDTO facilityDTO) {
        Facility facility = mapEntity(facilityDTO);

        Facility newFacility = facilityRepository.save(facility);

        FacilityDTO facilityResponse = mapDTO(newFacility);
        return facilityResponse;
    }

    @Override
    public FacilityDTO update(FacilityDTO facilityDTO, Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility", "id",id));

        facility.setName(facilityDTO.getName());
        facility.setStatus(facilityDTO.getStatus());

        Facility facilityUpdate = facilityRepository.save(facility);

        return mapDTO(facilityUpdate);
    }



    @Override
    public void delete(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility", "id",id));
        facilityRepository.delete(facility);
    }





    // Convierte entidad a DTO
    private FacilityDTO mapDTO(Facility facility) {
        FacilityDTO facilityDTO = modelMapper.map(facility, FacilityDTO.class);
        return facilityDTO;
    }

    // Convierte de DTO a Entidad
    private Facility mapEntity(FacilityDTO facilityDTO) {
        Facility facility = modelMapper.map(facilityDTO, Facility.class);
        return facility;
    }


}
