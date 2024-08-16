package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dto.NeighborhoodWithCityNameDTO;
import com.example.entities.NeighborhoodEntity;

@Repository
public interface NeighborhoodRepository extends JpaRepository<NeighborhoodEntity, Long> {
	// Selecciona todos los Neighborhood y los presenta con el DTO
	// NeighborhoodWithCityNameDTO
	@Query("SELECT new com.example.dto.NeighborhoodWithCityNameDTO(n.id, n.name, n.postalCode, c.name) "
			+ "FROM NeighborhoodEntity n JOIN n.city c")
	List<NeighborhoodWithCityNameDTO> findAllNeighborhoodDTOsWithCityName();

	Optional<List<NeighborhoodEntity>> findByPostalCode(String postalCode);

	@Query("SELECT n FROM NeighborhoodEntity n")
	List<NeighborhoodEntity> findAllBasic();

	@Query("SELECT n FROM NeighborhoodEntity n JOIN FETCH n.city")
	List<NeighborhoodEntity> findAllWithCity();

	// Selecciona un Neighborhood por su id y lo presenta con el DTO
	// NeighborhoodWithCityNameDTO
	@Query("SELECT new com.example.dto.NeighborhoodWithCityNameDTO(n.id, n.name, n.postalCode, c.name) "
			+ "FROM NeighborhoodEntity n " + "JOIN n.city c " + "WHERE n.id = :id")
	Optional<NeighborhoodWithCityNameDTO> findNeighborhoodWithCityNameDTOById(@Param("id") Long id);

	Optional<NeighborhoodEntity> findByName(String name);

	Optional<NeighborhoodEntity> findByNameAndPostalCode(String name, String postalCode);
}
