package com.example.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.BasicEventDTO;
import com.example.dto.EventRecibeMultipartFileDTO;
import com.example.dto.EventWithNeighborhoodsAndCategoriesDTO;
import com.example.services.EventService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/events")
public class EventController {
	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public ResponseEntity<List<BasicEventDTO>> getAllBasicEvents() {
		List<BasicEventDTO> events = eventService.findAllBasicEvents();
		return ResponseEntity.ok(events);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEventById(@PathVariable Long id) {
		Optional<BasicEventDTO> existEventById = eventService.findEventById(id);
		return existEventById.isPresent() ? ResponseEntity.ok(existEventById) : ResponseEntity.notFound().build();
	}

	@GetMapping("/with-details")
	public List<EventWithNeighborhoodsAndCategoriesDTO> getAllEvents() {
		return eventService.findAllEventsTest();
	}

	/*
	 * curl -X PUT "http://localhost:8080/events/create" \ -H
	 * "Content-Type: multipart/form-data" \ -F "image=@/path/to/your/image.jpg" \
	 * -F "event={ \"name\":\"Event name\", \"description\":\"Event description\",
	 * \"date\":\"2024-07-20\", \"categoryIds\":[1,2] };type=application/json"
	 */
	@PutMapping(consumes = "multipart/form-data")
	public ResponseEntity<?> createEvent(@RequestPart("event") EventRecibeMultipartFileDTO eventDTO,
			@RequestPart("image") MultipartFile image) {
		try {
			final int MAX_FILE_SIZE = 500000;
			// Validate image
			if (image == null || image.isEmpty()) {
				return new ResponseEntity<>("Image file is missing or empty", HttpStatus.BAD_REQUEST);
			}

			if (image.getSize() > MAX_FILE_SIZE) { // Replace MAX_FILE_SIZE with your desired max size
				return new ResponseEntity<>("File size exceeds the maximum limit.", HttpStatus.BAD_REQUEST);
			}

			// Agrega la imagen al DTO
			eventDTO.setImage(image);

			// Llama al servicio para crear el evento
			ResponseEntity<?> responseEntity = eventService.createEvent(eventDTO);

			// Analiza el estado HTTP y retorna la respuesta apropiada
			if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
				return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeEvent(@PathVariable Long id) {
		return eventService.deleteEvent(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}