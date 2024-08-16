package com.example.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.example.dto.BasicEventDTO;
import com.example.dto.CategoryDTO;
import com.example.dto.EventRecibeMultipartFileDTO;
import com.example.dto.EventWithNeighborhoodsAndCategoriesDTO;
import com.example.dto.NeighborhoodDTO;
import com.example.entities.CategoryEntity;
import com.example.entities.EventEntity;
import com.example.repository.CategoryRepository;
import com.example.repository.EventRepository;
import com.example.repository.NeighborhoodRepository;

@Service
public class EventService {
	@Value("${spring.cloud.azure.storage.blob.container-name}")
	private String containerName;

	private final BlobServiceClient blobServiceClient;
	private final EventRepository eventRepository;
	private final CategoryRepository categoryRepository;

	public EventService(BlobServiceClient blobServiceClient, EventRepository eventRepository,
			CategoryRepository categoryRepository, NeighborhoodRepository neighborhoodRepository) {
		this.blobServiceClient = blobServiceClient;
		this.eventRepository = eventRepository;
		this.categoryRepository = categoryRepository;
	}

	public List<BasicEventDTO> findAllBasicEvents() {
		return eventRepository.findAll().stream().map(event -> new BasicEventDTO(event.getId(), event.getName(),
				event.getDescription(), event.getDate(), event.getImageUrl())).collect(Collectors.toList());
	}

	public Optional<BasicEventDTO> findEventById(Long id) {
		Optional<EventEntity> eventEntity = eventRepository.findById(id);
		Optional<BasicEventDTO> basicEventDTO = Optional.ofNullable(new BasicEventDTO());
		if(eventEntity.isPresent()) {
			basicEventDTO.get().setId(eventEntity.get().getId());
			basicEventDTO.get().setName(eventEntity.get().getName());
			basicEventDTO.get().setDescription(eventEntity.get().getDescription());
			basicEventDTO.get().setDate(eventEntity.get().getDate());
			basicEventDTO.get().setImageUrl(eventEntity.get().getImageUrl());
		}
		return basicEventDTO;
	}

	public List<EventWithNeighborhoodsAndCategoriesDTO> findAllEventsTest() {
		List<EventEntity> events = eventRepository.findAll();
		return events.stream().map(event -> new EventWithNeighborhoodsAndCategoriesDTO(event.getId(), event.getName(),
				event.getDescription(), event.getDate(), event.getImageUrl(),
				event.getCategories().stream().map(category -> new CategoryDTO(category.getId(), category.getName()))
						.collect(Collectors.toList()),
				event.getNeighborhoods().stream()
						.map(neighborhood -> new NeighborhoodDTO(neighborhood.getId(), neighborhood.getName(),
								neighborhood.getPostalCode()))
						.collect(Collectors.toList())))
				.collect(Collectors.toList());
	}

	public ResponseEntity<?> createEvent(EventRecibeMultipartFileDTO eventDTO) throws IOException {
		String imageUrl = uploadImageToAzureBlob(eventDTO.getImage());

		EventEntity eventEntity = new EventEntity();
		eventEntity.setName(eventDTO.getName());
		eventEntity.setDescription(eventDTO.getDescription());
		eventEntity.setDate(eventDTO.getDate());

		// Crea un conjunto de categorías
		List<CategoryEntity> categories = new ArrayList<>();
		for (Long categoryId : eventDTO.getCategoryIds()) {
			Optional<CategoryEntity> categoryEntityOpt = categoryRepository.findById(categoryId);
			if (categoryEntityOpt.isPresent()) {
				categories.add(categoryEntityOpt.get());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category not found for ID: " + categoryId);
			}
		}
		eventEntity.setCategories(categories);
		// Guarda la imagen en Azure Blob Storage y setea la URL en la entidad Evento
		eventEntity.setImageUrl(imageUrl);

		// Guarda el evento en el repositorio
		EventEntity savedEvent = eventRepository.save(eventEntity);

		// Devuelve la respuesta con el evento guardado
		return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
	}

	private String uploadImageToAzureBlob(MultipartFile image) throws IOException {
		BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName)
				.getBlobClient(image.getOriginalFilename());

		blobClient.upload(image.getInputStream(), image.getSize(), true);
		return blobClient.getBlobUrl();
	}

	public boolean deleteEvent(Long id) {
		if (eventRepository.existsById(id)) {
			eventRepository.deleteById(id);
			return true;
		}
		return false;
	}
}