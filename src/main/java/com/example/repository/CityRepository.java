package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.entities.CityEntity;

@EnableJpaRepositories
@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
	// Método para buscar una ciudad por nombre
	CityEntity findByName(String name);
}
