package com.example.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicEventDTO implements Serializable {
	private static final long serialVersionUID = 4943873800091698701L;

	private Long id;
	private String name;
	private String description;
	private Date date;
	private String imageUrl;
}