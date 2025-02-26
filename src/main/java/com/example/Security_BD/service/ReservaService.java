package com.example.Security_BD.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Security_BD.model.Clase;
import com.example.Security_BD.model.Reserva;
import com.example.Security_BD.model.Usuario;
import com.example.Security_BD.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva reservar(Usuario usuario, Clase clase) {
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setClase(clase);
        reserva.setFechaReserva(LocalDateTime.now());

        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarPorUsuario(Usuario usuario) {
        return reservaRepository.findByUsuario(usuario);
    }
}
