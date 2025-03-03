package com.dpm.controllers;

import com.dpm.dto.ResumenPersonas;
import com.dpm.models.Persona;
import com.dpm.services.PersonaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author danielpm.dev
 */
@Controller
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/resumen")
    public String mostrarResumen(Model model) {
        List<ResumenPersonas> resumen = personaService.obtenerResumenPersonas();
        model.addAttribute("resumenPersonas", resumen);
        return "resumenTareas";
    }
    
    @GetMapping("/personas")
    public String findAllPersonas(Model model) {
        model.addAttribute("personas", personaService.getAllPersonas());
        return "persona-list";
    }

    @GetMapping("/personas/{id}")
    public String findPersonasByID(Model model, @PathVariable Long id) {
        if (personaService.getPersonaById(id).isPresent()) {
            model.addAttribute("persona", personaService.getPersonaById(id).get());
            return "persona-view";
        }
        return "persona-view";
    }

    @GetMapping("personas/new")
    public String getEmptyForm(Model model) {
        model.addAttribute("persona", new Persona());
        return "persona-form";
    }

    @GetMapping("personas/edit/{id}")
    public String getFormEdit(Model model, @PathVariable Long id) {
        if (personaService.existsById(id)) {
            personaService.getPersonaById(id).ifPresent(p -> {
                model.addAttribute("persona", p);
            });

            return "persona-form";
        } else {
            return "redirect:/personas/new";
        }
    }

    @PostMapping("/personas")
    public String createBook(@ModelAttribute Persona persona) {
        if (persona.getId() != null){
            //Actualización
            personaService.getPersonaById(persona.getId()).ifPresent(p -> {
                p.setNombre(persona.getNombre());
                p.setCorreo(persona.getCorreo());
                personaService.createOrUpdatePersona(p);
            });
        } else {
            //Creación
            personaService.createOrUpdatePersona(persona);
        }
        return "redirect:/personas"; //Mover a la lista de personas cuando ya se haya creado
    }

    @GetMapping("/personas/tareas/{id}")
    public String tarasPersonasById(@PathVariable Long id, Model model) {
        if (personaService.existsById(id)) {
            personaService.getPersonaById(id).ifPresent(p -> {
                model.addAttribute("persona", p);
            });

            return "persona-tareas";
        }
        return "redirect:/personas";
    }

    @GetMapping("/personas/delete/{id}")
    public String deletePersonasById(@PathVariable Long id) {
//        if(personaService.existsById(id)){
//            personaService.deletePersonaById(id);
//        }
        return "redirect:/personas";
    }
}

