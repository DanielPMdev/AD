package com.dpm.controllers;

import com.dpm.models.Estado;
import com.dpm.models.Tarea;
import com.dpm.services.PersonaService;
import com.dpm.services.TareaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Controller
public class TareaController {

    private final TareaService tareaService;
    private final PersonaService personaService;

    public TareaController(TareaService tareaService, PersonaService personaService) {
        this.tareaService = tareaService;
        this.personaService = personaService;
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/tareas";
    }

    @GetMapping("/tareas")
    public String findAllTareas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long personaId,
            Model model) {
        List<Tarea> tareas;

        // Aplicar filtros según los parámetros recibidos
        if (estado != null && !estado.isEmpty() && personaId != null) {
            tareas = tareaService.getTareasByEstadoAndPersona(estado, personaId);
        } else if (estado != null && !estado.isEmpty()) {
            tareas = tareaService.getTareasByEstado(estado);
        } else if (personaId != null) {
            tareas = tareaService.getTareasByPersona(personaId);
        } else {
            tareas = tareaService.getAllTareas(); // Sin filtros, muestra todas
        }

        // Agregar tareas y personas al modelo
        model.addAttribute("tareas", tareas);
        model.addAttribute("personas", personaService.getAllPersonas());

        return "tarea-list"; // Nombre de tu vista
    }

    @GetMapping("/tareas/{id}")
    public String findTareasByID(Model model, @PathVariable Long id) {
        if (tareaService.getTareaById(id).isPresent()) {
            model.addAttribute("tarea", tareaService.getTareaById(id).get());
            return "tarea-view";
        }
        return "tarea-view";
    }

    @GetMapping("/tareas/new")
    public String getEmptyForm(Model model) {
        model.addAttribute("tarea", new Tarea());
        model.addAttribute("personas", personaService.getAllPersonas());
        return "tarea-form";
    }

    @GetMapping("/tareas/edit/{id}")
    public String getFormEdit(Model model, @PathVariable Long id) {
        if (tareaService.existsById(id)){
            tareaService.getTareaById(id).ifPresent(b ->
                    model.addAttribute("tarea", b));
            model.addAttribute("personas", personaService.getAllPersonas());
            return "tarea-form";
        } else {
            return "redirect:/tareas/new";
        }
    }

    @PostMapping("/tareas")
    public String createTarea(@ModelAttribute Tarea tarea) {
        if (tarea.getId() != null){
            //Actualización
            tareaService.getTareaById(tarea.getId()).ifPresent(t -> {
                t.setDescripcion(tarea.getDescripcion());
                t.setPersona(tarea.getPersona());

                tareaService.createOrUpdateTarea(t);
            });
        } else {
            //Creación
            tarea.setEstado(Estado.PENDIENTE);
            tareaService.createOrUpdateTarea(tarea);
        }
        return "redirect:/tareas"; //Mover a la lista de tareas cuando ya se haya creado
    }

    @PostMapping("/tareas/cambiar-estado/{id}")
    public String cambiarEstadoTarea(@PathVariable Long id) {
        Optional<Tarea> tareaOpt = tareaService.getTareaById(id);

        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            switch (tarea.getEstado()) {
                case PENDIENTE:
                    tarea.setEstado(Estado.DESARROLLO);
                    break;
                case DESARROLLO:
                    tarea.setEstado(Estado.COMPLETADA);
                    break;
                case COMPLETADA:
                    break;
            }
            tareaService.createOrUpdateTarea(tarea);

        }
        return "redirect:/tareas";
    }

    @GetMapping("/tareas/delete/{id}")
    public String deleteTareaById(@PathVariable Long id) {
        if(tareaService.existsById(id)){
            tareaService.deleteTareaById(id);
        }
        return "redirect:/tareas";
    }

}