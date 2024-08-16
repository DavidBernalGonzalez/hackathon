package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CategoryWithEventsDTO;
import com.example.entities.CategoryEntity;
import com.example.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping // ✅
	public List<CategoryEntity> getAllCategories() {
		return categoryService.findAll();
	}

	@GetMapping("/{id}") // ✅
	public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
		Optional<CategoryEntity> categoryById = categoryService.findCategoryById(id);
		return categoryService.findCategoryById(id).isPresent() ? ResponseEntity.ok(categoryById)
				: ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}") // ✅
	public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
		return categoryService.deleteCategoryById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@GetMapping("/with-details")
	public List<CategoryWithEventsDTO> getAllCategoriesWithDetails() {
		return categoryService.getAllCategoriesWithDetails();
	}

	@PutMapping // ✅
	public ResponseEntity<?> createCategory(@RequestBody CategoryEntity categoryEntity) {
		CategoryEntity existCategory = categoryService.saveCategory(categoryEntity);
		return existCategory != null ? ResponseEntity.ok(existCategory)
				: ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
