package com.example.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodWithCityNameDTO implements Serializable{
	private static final long serialVersionUID = -407751603710367516L;
	
	private Long id;
	private String name;
	private String postalCode;
	private String cityName;
}