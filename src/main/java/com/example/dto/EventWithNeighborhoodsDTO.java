package com.example.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventWithNeighborhoodsDTO {
    private Long id;
    private String name;
    private String description;
    private Date date;
    private String imageUrl;
    private List<NeighborhoodWithCityDTO> neighborhoods;
}
