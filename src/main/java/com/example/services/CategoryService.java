package com.example.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.CategoryWithEventsDTO;
import com.example.dto.CityDTO;
import com.example.dto.EventWithNeighborhoodsDTO;
import com.example.dto.NeighborhoodWithCityDTO;
import com.example.entities.CategoryEntity;
import com.example.entities.CityEntity;
import com.example.entities.EventEntity;
import com.example.entities.NeighborhoodEntity;
import com.example.exceptions.DuplicateCityException;
import com.example.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryEntity> findAll() {
		return categoryRepository.findAll();
	}

	public Optional<CategoryEntity> findCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

	public List<CategoryWithEventsDTO> getAllCategoriesWithDetails() {
		List<CategoryEntity> categories = categoryRepository.findAll();
		return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private CategoryWithEventsDTO convertToDTO(CategoryEntity category) {
		CategoryWithEventsDTO dto = new CategoryWithEventsDTO();
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setEvents(category.getEvents().stream().map(this::convertEventToDTO).collect(Collectors.toList()));
		return dto;
	}

	private EventWithNeighborhoodsDTO convertEventToDTO(EventEntity event) {
		EventWithNeighborhoodsDTO dto = new EventWithNeighborhoodsDTO();
		dto.setId(event.getId());
		dto.setName(event.getName());
		dto.setDescription(event.getDescription());
		dto.setDate(event.getDate());
		dto.setImageUrl(event.getImageUrl());
		dto.setNeighborhoods(
				event.getNeighborhoods().stream().map(this::convertNeighborhoodToDTO).collect(Collectors.toList()));
		return dto;
	}

	private NeighborhoodWithCityDTO convertNeighborhoodToDTO(NeighborhoodEntity neighborhood) {
		NeighborhoodWithCityDTO dto = new NeighborhoodWithCityDTO();
		dto.setId(neighborhood.getId());
		dto.setName(neighborhood.getName());
		dto.setPostalCode(neighborhood.getPostalCode());
		dto.setCity(convertCityToDTO(neighborhood.getCity()));
		return dto;
	}

	private CityDTO convertCityToDTO(CityEntity city) {
		CityDTO dto = new CityDTO();
		dto.setId(city.getId());
		dto.setName(city.getName());
		return dto;
	}

	public CategoryEntity saveCategory(CategoryEntity categoryEntity) {
		Optional<CategoryEntity> existsCategory = categoryRepository.findByName(categoryEntity.getName());
		if (existsCategory.isPresent()) {
			throw new DuplicateCityException("La categoria ya existe");
		}
		categoryRepository.save(categoryEntity);
		return categoryEntity;
	}

	public boolean deleteCategoryById(Long id) {
		if (categoryRepository.existsById(id)) {
			categoryRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
