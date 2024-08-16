package com.example.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventWithNeighborhoodsAndCategoriesDTO implements Serializable{
	private static final long serialVersionUID = -1999774220946139722L;
	
	private Long id;
	private String name;
	private String description;
	private Date date;
	private String imageUrl;
	private List<CategoryDTO> categories;
	private List<NeighborhoodDTO> neighborhoods;
}