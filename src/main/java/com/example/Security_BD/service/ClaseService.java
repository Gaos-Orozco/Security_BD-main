package com.example.Security_BD.service;

import com.example.Security_BD.model.Clase;
import com.example.Security_BD.repository.ClaseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    public List<Clase> obtenerTodas() {
        return claseRepository.findAll();
    }

    public Optional<Clase> obtenerPorId(Long id) {
        return claseRepository.findById(id);
    }

    public Clase guardarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    public void eliminarClase(Long id) {
        claseRepository.deleteById(id);
    }
}
