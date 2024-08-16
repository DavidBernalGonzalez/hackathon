package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CityWithNeighborhoodDTO;
import com.example.entities.CityEntity;
import com.example.services.CityService;

@RestController
@RequestMapping("/api/cities")
public class CityController {
	private CityService cityService;
	
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping // ✅
	public List<CityEntity> getAll() {
		return cityService.findAll();
	}

	@GetMapping("/{id}") // ✅
	public ResponseEntity<?> getCityById(@PathVariable Long id) {
		Optional<CityEntity> cityFindedById = cityService.findById(id);
		return cityFindedById.isPresent() ? ResponseEntity.ok(cityFindedById.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping("/with-neighborhoods") // ✅
	public List<CityWithNeighborhoodDTO> getAllCitiesWithNeighborhoods() {
		return cityService.findAllCitiesWithNeighborhoods();
	}

	@GetMapping("/with-neighborhoods/{id}") // ✅
	public ResponseEntity<CityWithNeighborhoodDTO> getCityWithNeighborhoodsById(@PathVariable Long id) {
		CityWithNeighborhoodDTO cityWithNeighborhoodDTO = cityService.findCityWithNeighborhoodsById(id);
		return cityWithNeighborhoodDTO != null ? ResponseEntity.ok(cityWithNeighborhoodDTO)
				: ResponseEntity.notFound().build();
	}

	@PutMapping // ✅
	public ResponseEntity<?> create(@RequestBody CityEntity city) {
		return cityService.saveCity(city) ? ResponseEntity.status(HttpStatus.CONFLICT).build() : null;
	}

	@PatchMapping // ✅
	public ResponseEntity<?> updateCity(@RequestBody CityEntity cityToUpdate) {
		Optional<CityEntity> existingCity = cityService.findById(cityToUpdate.getId());
		if (existingCity.isPresent()) {
			return ResponseEntity.ok(cityService.saveCity(cityToUpdate));
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}") // ✅
	public ResponseEntity<?> deleteCity(@PathVariable Long id) {
		return cityService.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}