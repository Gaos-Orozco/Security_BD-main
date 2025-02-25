package com.example.Security_BD.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Security_BD.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional <Usuario> findByUsername(String username);

}
