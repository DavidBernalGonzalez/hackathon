package com.example.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.CityWithNeighborhoodDTO;
import com.example.dto.NeighborhoodDTO;
import com.example.entities.CityEntity;
import com.example.exceptions.DuplicateCityException;
import com.example.repository.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;

	public List<CityEntity> findAll() {
		return cityRepository.findAll();
	}

	public List<CityWithNeighborhoodDTO> findAllCitiesWithNeighborhoods() {
		List<CityEntity> cities = cityRepository.findAll(); // Fetch all cities

        return cities.stream()
                .map(city -> {
                    List<NeighborhoodDTO> neighborhoodDTOs = city.getNeighborhoods().stream()
                            .map(neighborhood -> new NeighborhoodDTO(
                                neighborhood.getId(),
                                neighborhood.getName(),
                                neighborhood.getPostalCode()
                            ))
                            .collect(Collectors.toList());

                    return new CityWithNeighborhoodDTO(
                        city.getId(),
                        city.getName(),
                        neighborhoodDTOs
                    );
                })
                .collect(Collectors.toList());
	}

	public CityWithNeighborhoodDTO findCityWithNeighborhoodsById(Long id) {
        Optional<CityEntity> cityOptional = cityRepository.findById(id);

        if (cityOptional.isPresent()) {
            CityEntity city = cityOptional.get();

            List<NeighborhoodDTO> neighborhoodDTOs = city.getNeighborhoods().stream()
                    .map(neighborhood -> new NeighborhoodDTO(
                        neighborhood.getId(),
                        neighborhood.getName(),
                        neighborhood.getPostalCode()
                    ))
                    .collect(Collectors.toList());

            return new CityWithNeighborhoodDTO(
                city.getId(),
                city.getName(),
                neighborhoodDTOs
            );
        } else {
            return null;
        }
    }

	public Optional<CityEntity> findById(Long id) {
		return cityRepository.findById(id);
	}

	public boolean saveCity(CityEntity city) {
		if (cityRepository.findByName(city.getName()) != null) {
			throw new DuplicateCityException("La ciudad ya existe");
		}
		cityRepository.save(city);
		return false;
	}

	public boolean deleteById(Long id) {
		if (cityRepository.existsById(id)) {
			cityRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
