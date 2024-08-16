package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.NeighborhoodDTO;
import com.example.dto.NeighborhoodDTOWithCityEntity;
import com.example.dto.NeighborhoodWithCityIdDTO;
import com.example.dto.NeighborhoodWithCityNameDTO;
import com.example.entities.NeighborhoodEntity;
import com.example.repository.CityRepository;
import com.example.services.NeighborhoodService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/neighborhoods", produces = "application/json")
public class NeighborhoodController {
	private NeighborhoodService neighborhoodService;
	private CityRepository cityRepository;

	public NeighborhoodController(NeighborhoodService neighborhoodService, CityRepository cityRepository) {
		this.neighborhoodService = neighborhoodService;
		this.cityRepository = cityRepository;
	}

	@GetMapping
	public List<NeighborhoodDTO> getAllBasicNeighborhoods() {
		List<NeighborhoodDTO> dtoList = neighborhoodService.findAllBasic();
		return dtoList;
	}

	@GetMapping("/with-city-entity")
	public ResponseEntity<List<?>> getAllNeighborhoodsWithCityEntity() {
		List<NeighborhoodDTOWithCityEntity> dtoList = neighborhoodService.findAllWithCity();
		return ResponseEntity.ok(dtoList);
	}

	@GetMapping("cityId/{id}") // ✅
	public ResponseEntity<?> getNeighborhoodById(@PathVariable Long id) {
		Optional<NeighborhoodEntity> neighborhood = neighborhoodService.findNeighborhoodById(id);
		return (neighborhood.isPresent() ? ResponseEntity.ok(neighborhood.get()) : ResponseEntity.notFound().build());
	}

	@GetMapping("/{postalCode}") // ✅
	public ResponseEntity<List<NeighborhoodEntity>> getNeighborhoodByPostalCode(@PathVariable String postalCode) {
		Optional<List<NeighborhoodEntity>> neighborhoodList = neighborhoodService
				.findNeighborhoodByPostalCode(postalCode);
		return (neighborhoodList.get().size() > 0 ? ResponseEntity.ok(neighborhoodList.get())
				: ResponseEntity.notFound().build());
	}

	@GetMapping("/with-city-name") // ✅
	public ResponseEntity<List<NeighborhoodWithCityNameDTO>> getAllNeighborhoodDTOsWithCityName() {
		Optional<List<NeighborhoodWithCityNameDTO>> neighborhoodWithCityDTO = neighborhoodService
				.findNeighborhoodDTOWithCity();
		return neighborhoodWithCityDTO.isPresent() ? ResponseEntity.ok(neighborhoodWithCityDTO.get())
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/with-city-name/{id}") // ✅
	public ResponseEntity<NeighborhoodWithCityNameDTO> getAllNeighborhoodDTOsWithCityNameById(@PathVariable Long id) {
		Optional<NeighborhoodWithCityNameDTO> cityWithNeighborhoodDTO = neighborhoodService
				.findNeighborhoodDTOWithCityById(id);
		return cityWithNeighborhoodDTO.isPresent() ? ResponseEntity.ok(cityWithNeighborhoodDTO.get())
				: ResponseEntity.notFound().build();
	}

	@PutMapping // ✅
	@Transactional
	public ResponseEntity<?> create(@RequestBody NeighborhoodWithCityIdDTO neighborhoodWithCityIdDTO) {
		return neighborhoodService.save(neighborhoodWithCityIdDTO);
	}

	@PatchMapping // ✅
	public ResponseEntity<?> update(@RequestBody NeighborhoodEntity neighborhoodEntity) {
		neighborhoodEntity.validateFields(true); // Llama al método de validación

		// Verificar que la ciudad está persistida
		if (!cityRepository.existsById(neighborhoodEntity.getCity().getId())) {
			throw new IllegalArgumentException("The specified City entity does not exist in the database.");
		}

		Optional<NeighborhoodEntity> existingNeighborhood = neighborhoodService
				.findNeighborhoodById(neighborhoodEntity.getId());
		if (existingNeighborhood.isPresent()) {
			NeighborhoodEntity updatedNeighborhood = neighborhoodService.update(neighborhoodEntity);
			return ResponseEntity.ok(updatedNeighborhood);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}") // ✅
	public ResponseEntity<?> deleteNeighborhood(@PathVariable Long id) {
		return neighborhoodService.deleteNeighborhoodById(id) ? ResponseEntity.ok().build()
				: ResponseEntity.notFound().build();
	}

}