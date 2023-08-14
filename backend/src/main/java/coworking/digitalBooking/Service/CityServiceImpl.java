package coworking.digitalBooking.Service;

import coworking.digitalBooking.Dto.CityDTO;
import coworking.digitalBooking.Entities.City;
import coworking.digitalBooking.Exceptions.ResourceNotFoundException;
import coworking.digitalBooking.Repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService{


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public CityDTO findByName(String name) {
        City city = cityRepository.findByName(name);
        return mapDTO(city);
    }

    @Override
    public CityDTO searchById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id",id));
        return mapDTO(city);
    }

    @Override
    public List<CityDTO> searchAll() {
        List<City> cities = cityRepository.findAll();
        return cities.stream().map(city -> mapDTO(city)).collect(Collectors.toList());
    }

    @Override
    public CityDTO registerCity(CityDTO cityDTO) {
        City city = mapEntity(cityDTO);

        City newCity = cityRepository.save(city);

        CityDTO cityResponse = mapDTO(newCity);
        return cityResponse;
    }

    @Override
    public CityDTO update(CityDTO cityDTO, Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id",id));

        city.setName(cityDTO.getName());
        city.setCountry(cityDTO.getCountry());

        City cityUpdate = cityRepository.save(city);

        return mapDTO(cityUpdate);
    }



    @Override
    public void delete(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id",id));
        cityRepository.delete(city);
    }





    // Convierte entidad a DTO
    private CityDTO mapDTO(City city) {
        CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }

    // Convierte de DTO a Entidad
    private City mapEntity(CityDTO cityDTO) {
        City city = modelMapper.map(cityDTO, City.class);
        return city;
    }


}
