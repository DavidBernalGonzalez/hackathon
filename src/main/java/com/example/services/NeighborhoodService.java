package com.example.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.NeighborhoodDTO;
import com.example.dto.NeighborhoodDTOWithCityEntity;
import com.example.dto.NeighborhoodWithCityIdDTO;
import com.example.dto.NeighborhoodWithCityNameDTO;
import com.example.entities.CityEntity;
import com.example.entities.NeighborhoodEntity;
import com.example.repository.CityRepository;
import com.example.repository.NeighborhoodRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class NeighborhoodService {
	private NeighborhoodRepository neighborhoodRepository;

	private CityRepository cityRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public NeighborhoodService(NeighborhoodRepository neighborhoodRepository, CityRepository cityRepository) {
		this.neighborhoodRepository = neighborhoodRepository;
		this.cityRepository = cityRepository;
	}

	public List<NeighborhoodDTOWithCityEntity> findAllWithCity() {
        List<NeighborhoodEntity> entities = neighborhoodRepository.findAllWithCity();
        return entities.stream()
                       .map(this::convertToDTOWithCity)
                       .collect(Collectors.toList());
    }

    public List<NeighborhoodDTO> findAllBasic() {
        List<NeighborhoodEntity> entities = neighborhoodRepository.findAllBasic();
        return entities.stream()
                       .map(this::convertToBasicDTO)
                       .collect(Collectors.toList());
    }

    public NeighborhoodDTOWithCityEntity convertToDTOWithCity(NeighborhoodEntity entity) {
        return new NeighborhoodDTOWithCityEntity(
            entity.getId(),
            entity.getName(),
            entity.getPostalCode(),
            entity.getCity()
        );
    }

    public NeighborhoodDTO convertToBasicDTO(NeighborhoodEntity entity) {
        return new NeighborhoodDTO(
            entity.getId(),
            entity.getName(),
            entity.getPostalCode()
        );
    }

    public List<NeighborhoodDTOWithCityEntity> convertToDTOsWithCity(List<NeighborhoodEntity> entities) {
        return entities.stream()
                .map(this::convertToDTOWithCity)
                .collect(Collectors.toList());
    }

    public List<NeighborhoodDTO> convertToBasicDTOs(List<NeighborhoodEntity> entities) {
        return entities.stream()
                .map(this::convertToBasicDTO)
                .collect(Collectors.toList());
    }

	public Optional<NeighborhoodEntity> findNeighborhoodById(Long id) {
		return neighborhoodRepository.findById(id);
	}

	public Optional<NeighborhoodWithCityNameDTO> findNeighborhoodDTOWithCityById(Long id) {
		return neighborhoodRepository.findNeighborhoodWithCityNameDTOById(id);
//		return new NeighborhoodWithCityDTO(neighborhood.getId(), neighborhood.getName(), neighborhood.getPostalCode(), neighborhood.getName());
	}

	public Optional<List<NeighborhoodEntity>> findNeighborhoodByPostalCode(String postalCode) {
		return neighborhoodRepository.findByPostalCode(postalCode);
	}

	public ResponseEntity<?> save(NeighborhoodWithCityIdDTO neighborhoodWithCityIdDTO) {
		// Convert DTO to Entity
		NeighborhoodEntity neighborhoodEntity = new NeighborhoodEntity();
		neighborhoodEntity.setName(neighborhoodWithCityIdDTO.getName());
		neighborhoodEntity.setPostalCode(neighborhoodWithCityIdDTO.getPostalCode());

		Optional<CityEntity> cityEntityOptional = cityRepository.findById(neighborhoodWithCityIdDTO.getCityid());
		if (!cityEntityOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().body("The specified City with id "
					+ neighborhoodWithCityIdDTO.getCityid() + " entity does not exist in the database.");
		}
		neighborhoodEntity.setCity(cityEntityOptional.get());

		// Validar campos
		neighborhoodEntity.validateFields(false);

		// Verificar si ya existe un barrio con el mismo nombre y código postal
		Optional<NeighborhoodEntity> existingNeighborhood = neighborhoodRepository
				.findByNameAndPostalCode(neighborhoodEntity.getName(), neighborhoodEntity.getPostalCode());
		Optional<NeighborhoodEntity> existingNeighborhoodByName = neighborhoodRepository
				.findByName(neighborhoodEntity.getName());

		if (existingNeighborhood.isPresent() || existingNeighborhoodByName.isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe un barrio con el mismo nombre");
		}

		// Persistir la entidad
		NeighborhoodEntity managedEntity = entityManager.merge(neighborhoodEntity);
		entityManager.persist(managedEntity);

		return ResponseEntity.ok(managedEntity);
	}

	public NeighborhoodEntity update(NeighborhoodEntity neighborhoodEntity) {
		Optional<NeighborhoodEntity> existingNeighborhood = neighborhoodRepository.findById(neighborhoodEntity.getId());
		if (existingNeighborhood.isPresent()) {
			NeighborhoodEntity updatedNeighborhood = existingNeighborhood.get();
			// Actualizar los campos necesarios
			updatedNeighborhood.setName(neighborhoodEntity.getName());
			updatedNeighborhood.setPostalCode(neighborhoodEntity.getPostalCode());
//			updatedNeighborhood.setCity(neighborhoodEntity.getCity());
			// Guardar los cambios
			return neighborhoodRepository.save(updatedNeighborhood);
		} else {
			throw new RuntimeException("Neighborhood not found with id " + neighborhoodEntity.getId());
		}
	}

	public boolean deleteNeighborhoodById(Long id) {
		if (neighborhoodRepository.existsById(id)) {
			neighborhoodRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Optional<List<NeighborhoodWithCityNameDTO>> findNeighborhoodDTOWithCity() {
		return Optional.ofNullable(neighborhoodRepository.findAllNeighborhoodDTOsWithCityName());
	}
}
