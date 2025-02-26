package com.example.Security_BD.Controller;

import java.time.LocalDateTime;
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

@Controller
@RequestMapping("/admin/clases")
public class AdminClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public String listarClases(Model model) {
        model.addAttribute("clases", claseService.obtenerTodas());
        return "admin"; // Vista para administradores (admin.html)
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("clase", new Clase());
        return "formulario_clase"; // Formulario para crear una clase
    }

    @PostMapping("/crear")
    public String crearClase(@ModelAttribute Clase clase, RedirectAttributes redirectAttributes) {
        claseService.guardarClase(clase);
        redirectAttributes.addFlashAttribute("mensaje", "Clase creada exitosamente");
        return "redirect:/admin/clases";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
        model.addAttribute("clase", clase);
        return "formulario_clase";
    }

    @PostMapping("/{id}/editar")
    public String editarClase(@PathVariable Long id, @ModelAttribute Clase clase, RedirectAttributes redirectAttributes) {
        clase.setId(id);
        claseService.guardarClase(clase);
        redirectAttributes.addFlashAttribute("mensaje", "Clase actualizada correctamente");
        return "redirect:/admin/clases";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarClase(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        claseService.eliminarClase(id);
        redirectAttributes.addFlashAttribute("mensaje", "Clase eliminada correctamente");
        return "redirect:/admin/clases";
    }

    @GetMapping("/{id}/usuarios")
    public String verUsuariosEnClase(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
        model.addAttribute("clase", clase);
        // Se asume que la entidad Clase tiene una relación con Reserva y que Reserva contiene el Usuario
        model.addAttribute("usuarios", clase.getReservas().stream().map(Reserva::getUsuario).collect(Collectors.toList()));
        return "usuarios_en_clase";
    }


    
    
    @Controller
public class ClaseController {

    @Autowired
    private ClaseRepository claseRepository;

    @PostMapping("/clase/guardar")
    public String guardarClase(@RequestParam String nombre, 
                               @RequestParam String fechaHora, 
                               RedirectAttributes redirectAttributes) {
        Clase nuevaClase = new Clase();
        nuevaClase.setNombre(nombre);
        nuevaClase.setFecha(LocalDateTime.parse(fechaHora));

        claseRepository.save(nuevaClase);
        System.out.println("Clase guardada en BD: " + nuevaClase);

        redirectAttributes.addFlashAttribute("mensaje", "Clase registrada con éxito");
        return "redirect:/admin";
    }
}

}
