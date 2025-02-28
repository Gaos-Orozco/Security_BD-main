package com.example.Security_BD.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Security_BD.model.Clase;
import com.example.Security_BD.model.Usuario;
import com.example.Security_BD.service.ClaseService;
import com.example.Security_BD.service.ReservaService;
import com.example.Security_BD.service.UsuarioService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClaseService claseService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/clases")
    public String listarClases(Model model) {
        model.addAttribute("clases", claseService.obtenerTodas());
        return "cliente"; 
    }


    @PostMapping("/reservar/{claseId}")
    public String reservarClase(@PathVariable Long claseId,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
     
        String username = authentication.getName();
        
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        
        Clase clase = claseService.obtenerPorId(claseId).orElse(null);
        if (clase == null) {
            redirectAttributes.addFlashAttribute("error", "La clase no existe.");
            return "redirect:/cliente/clases";
        }
        
        reservaService.reservar(usuario, clase);
        redirectAttributes.addFlashAttribute("mensaje", "Reserva realizada con éxito");
        return "redirect:/cliente/clases";
    }
    
    


}

