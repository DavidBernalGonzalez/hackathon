package com.example.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EventRecibeMultipartFileDTO implements Serializable{
	private static final long serialVersionUID = -5430762036707724137L;
	
	private String name;
    private String description;
    private Date date;
    private MultipartFile image;
    private List<Long> categoryIds;
    private List<Long> neighborhoodsIds;
}