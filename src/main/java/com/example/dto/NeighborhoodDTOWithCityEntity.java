package com.example.dto;

import com.example.entities.CityEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodDTOWithCityEntity {
    private Long id;
    private String name;
    private String postalCode;
    private CityEntity city;
}