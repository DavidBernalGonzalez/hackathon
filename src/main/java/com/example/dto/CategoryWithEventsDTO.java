package com.example.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithEventsDTO {
	private Long id;
	private String name;
	private List<EventWithNeighborhoodsDTO> events;
}
