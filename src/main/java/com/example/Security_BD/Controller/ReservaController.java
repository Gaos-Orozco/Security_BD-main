package com.example.Security_BD.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.example.Security_BD.model.Usuario;
import com.example.Security_BD.model.Clase;
import com.example.Security_BD.service.ClaseService;
import com.example.Security_BD.service.ReservaService;


@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public String listarReservas(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login"; 
        }
        model.addAttribute("reservas", reservaService.listarPorUsuario(usuario));
        return "mis_reservas";
    }

    @PostMapping("/reservar/{claseId}")
public String reservarClase(@PathVariable Long claseId, HttpSession session, RedirectAttributes redirectAttributes) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        return "redirect:/login";
    }

    Clase clase = claseService.obtenerPorId(claseId)
        .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));

    reservaService.reservar(usuario, clase);
    redirectAttributes.addFlashAttribute("mensaje", "Reserva realizada con éxito!");
    return "redirect:/reservas";
}


}