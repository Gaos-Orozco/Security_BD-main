package com.example.Security_BD.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Security_BD.model.Clase;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
Optional<Clase> findByNombre(String nombre);
}
