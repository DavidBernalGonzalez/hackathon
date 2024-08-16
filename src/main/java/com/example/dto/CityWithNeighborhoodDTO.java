package com.example.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityWithNeighborhoodDTO implements Serializable{
	private static final long serialVersionUID = 6666631221198813225L;

	private Long id;
	private String name;
	private List<NeighborhoodDTO> neighborhoods;
}