package com.example.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "neighborhood")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodEntity implements Serializable {
    private static final long serialVersionUID = -192457772551002891L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String postalCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @JsonBackReference
    private CityEntity city;

	public void validateFields(boolean checkId) {
		if (checkId == true && this.id == null) {
			throw new IllegalArgumentException("Neighborhood ID cannot be null");
		}
		if (name == null || this.name.isEmpty()) {
			throw new IllegalArgumentException("Neighborhood name cannot be null or empty");
		}
		if (this.postalCode == null || this.postalCode.isEmpty()) {
			throw new IllegalArgumentException("Neighborhood postalCode cannot be null or empty");
		}
//		if (this.city == null || this.city.getId() == null || city.getName() == null
//				|| this.city.getName().equals("")) {
//			throw new IllegalArgumentException("Neighborhood must have an associated City " + this.city);
//		}
	}
}
