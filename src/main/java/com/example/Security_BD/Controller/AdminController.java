package com.example.Security_BD.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Security_BD.model.Clase;
import com.example.Security_BD.model.Reserva;
import com.example.Security_BD.repository.ClaseRepository;
import com.example.Security_BD.service.ClaseService;
import com.example.Security_BD.service.ReservaService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClaseService claseService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ClaseRepository claseRepository;

    @GetMapping("/panel-reservas")
    public String verPanelReservas(Model model) {
    List<Clase> clases = claseService.obtenerTodas();
    List<Reserva> reservas = reservaService.obtenerTodasLasReservas();

    model.addAttribute("clases", clases);
    model.addAttribute("reservas", reservas);
    
    return "panel_reservas";
}

    @GetMapping("/clases")
    public String listarClases(Model model) {
        model.addAttribute("clases", claseService.obtenerTodas());
        return "admin"; 
    }

    @GetMapping("/clases/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("clase", new Clase());
        return "formulario_clase";
    }

    @PostMapping("/clases/crear")
    public String crearClase(@ModelAttribute Clase clase, RedirectAttributes redirectAttributes) {
        claseService.guardarClase(clase);
        redirectAttributes.addFlashAttribute("mensaje", "Clase creada exitosamente");
        return "redirect:/admin/clases";
    }

    @GetMapping("/clases/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
        model.addAttribute("clase", clase);
        return "formulario_clase";
    }

    @PostMapping("/clases/{id}/editar")
    public String editarClase(@PathVariable Long id, @ModelAttribute Clase clase, RedirectAttributes redirectAttributes) {
        clase.setId(id);
        claseService.guardarClase(clase);
        redirectAttributes.addFlashAttribute("mensaje", "Clase actualizada correctamente");
        return "redirect:/admin/clases";
    }

    @PostMapping("/clases/{id}/eliminar")
    public String eliminarClase(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        claseService.eliminarClase(id);
        redirectAttributes.addFlashAttribute("mensaje", "Clase eliminada correctamente");
        return "redirect:/admin/clases";
    }

    @GetMapping("/clases/{id}/usuarios")
    public String verUsuariosEnClase(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
        model.addAttribute("clase", clase);
        model.addAttribute("usuarios", clase.getReservas().stream().map(Reserva::getUsuario).collect(Collectors.toList()));
        return "usuarios_en_clase";
    }

    @PostMapping("/clase/guardar")
    public String guardarClase(@RequestParam String nombre, 
                               @RequestParam String fechaHora, 
                               RedirectAttributes redirectAttributes) {
        Clase nuevaClase = new Clase();
        nuevaClase.setNombre(nombre);
        nuevaClase.setFecha(LocalDateTime.parse(fechaHora));

        claseRepository.save(nuevaClase);
        redirectAttributes.addFlashAttribute("mensaje", "Clase registrada con éxito");
        return "redirect:/admin";
    }
}
