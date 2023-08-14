package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CoworkingFacilityDTO;
import coworking.digitalBooking.Dto.FacilityDTO;
import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.CoworkingFacility;
import coworking.digitalBooking.Entities.Facility;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.CoworkingFacilityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CoworkingFacilityImplement  implements CoworkingFacilityService{

    private final CoworkingFacilityRepository coworkingFacilityRepository;
    private final ModelMapper modelMapper;

    public CoworkingFacilityImplement(CoworkingFacilityRepository coworkingFacilityRepository, ModelMapper modelMapper) {
        this.coworkingFacilityRepository = coworkingFacilityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CoworkingFacilityDTO getCoworkingFacilityById(Long id) {
        CoworkingFacility coworkingFacility = coworkingFacilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CoworkingFacility", "id", id));
        return mapDTO(coworkingFacility);
    }

    @Override
    public List<CoworkingFacilityDTO> getAllCoworkingFacilities() {
        List<CoworkingFacility> coworkingPolicies = coworkingFacilityRepository.findAll();
        return coworkingPolicies.stream()
                .map(coworkingFacility -> mapDTO(coworkingFacility))
                .collect(Collectors.toList());
    }

    @Override
    public CoworkingFacilityDTO createCoworkingFacility(CoworkingFacilityDTO coworkingFacilityDTO) {
        CoworkingFacility coworkingFacility = mapEntity(coworkingFacilityDTO);
        CoworkingFacility createCoworkingFacility = coworkingFacilityRepository.save(coworkingFacility);
        CoworkingFacilityDTO coworkingFacilityResponse = mapDTO(createCoworkingFacility);
        return coworkingFacilityResponse;
    }

    @Override
    public CoworkingFacilityDTO updateCoworkingFacility(Long id, CoworkingFacilityDTO coworkingFacilityDTO) {
        CoworkingFacility coworkingFacilityUpdate= mapEntity(coworkingFacilityDTO);
        CoworkingFacility existingCoworkingFacility = coworkingFacilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CoworkingFacility", "id", id));


        existingCoworkingFacility.setCoworking(coworkingFacilityUpdate.getCoworking());
        existingCoworkingFacility.setFacility(coworkingFacilityUpdate.getFacility());

        CoworkingFacility updatedCoworkingFacility = coworkingFacilityRepository.save(existingCoworkingFacility);
        return mapDTO(updatedCoworkingFacility);
    }

    @Override
    public void deleteCoworkingFacility(Long id) {
        coworkingFacilityRepository.deleteById(id);
    }

    // Convierte entidad a DTO
    private CoworkingFacilityDTO mapDTO(CoworkingFacility coworkingFacility) {
        CoworkingFacilityDTO coworkingFacilityDTO = modelMapper.map(coworkingFacility, CoworkingFacilityDTO.class);
        return coworkingFacilityDTO;
    }

    // Convierte de DTO a Entidad
    private CoworkingFacility mapEntity(CoworkingFacilityDTO coworkingFacilityDTO) {
        CoworkingFacility coworkingFacility = modelMapper.map(coworkingFacilityDTO, CoworkingFacility.class);
        return coworkingFacility;
    }

}
