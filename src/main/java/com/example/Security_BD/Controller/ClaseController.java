package com.example.Security_BD.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Security_BD.model.Clase;
import com.example.Security_BD.service.ClaseService;

@Controller
@RequestMapping("/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public String listarClases(Model model) {
        model.addAttribute("clases", claseService.obtenerTodas());
        return "lista_clases";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("clase", new Clase());
        return "formulario_clase";
    }

    @PostMapping("/crear")
    public String crearClase(@ModelAttribute Clase clase, RedirectAttributes redirectAttributes) {
        claseService.guardarClase(clase);
        redirectAttributes.addFlashAttribute("mensaje", "Clase creada exitosamente!");
        return "redirect:/clases";
    }

    @GetMapping("/{id}/usuarios")
    public String verUsuariosClase(@PathVariable Long id, Model model) {
    Clase clase = claseService.obtenerPorId(id).get(); 
    model.addAttribute("clase", clase);
    model.addAttribute("usuarios", clase.getReservas());
    return "usuarios_en_clase";
}


    @PostMapping("/{id}/eliminar")
    public String eliminarClase(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        claseService.eliminarClase(id);
        redirectAttributes.addFlashAttribute("mensaje", "Clase eliminada con éxito!");
        return "redirect:/clases";
    }
}
