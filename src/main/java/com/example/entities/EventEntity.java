package com.example.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity implements Serializable {
	private static final long serialVersionUID = -1372728073525263758L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	private String description;
	private Date date;
	private String imageUrl;

	@ManyToMany
	@JoinTable(name = "event_has_category", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<CategoryEntity> categories;

	@ManyToMany
	@JoinTable(name = "event_has_neighborhood", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "neighborhood_id"))
	private List<NeighborhoodEntity> neighborhoods;
}

//private Set<NeighborhoodEntity> neighborhoods;

//@ManyToMany
//@JoinTable(name = "event_has_category", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
//private Set<CategoryEntity> categories = new HashSet<>();
