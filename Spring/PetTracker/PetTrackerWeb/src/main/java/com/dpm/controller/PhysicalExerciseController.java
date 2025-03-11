package com.dpm.controller;

import com.dpm.model.Pet;
import com.dpm.model.PhysicalExercise;
import com.dpm.model.Vaccine;
import com.dpm.service.PetService;
import com.dpm.service.PhysicalExerciseService;
import com.dpm.service.VaccineService;
import jakarta.servlet.http.HttpSession;
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
public class PhysicalExerciseController {

    private final PhysicalExerciseService physicalExerciseService;
    private final PetService petService;
    //private final OwnerService ownerService;

    public PhysicalExerciseController(PetService petService, PhysicalExerciseService physicalExerciseService) {
        this.petService = petService;
        this.physicalExerciseService = physicalExerciseService;
    }

    @GetMapping("/pet/{id}/exercises")
    public String showExercises(@PathVariable Long id, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                // Obtener la mascota y sus ejercicios
                Pet pet = petService.getPetById(token, id); // Asumiendo que tienes un servicio para esto
                if (pet == null) {
                    model.addAttribute("error", "Mascota no encontrada.");
                    return "mascotas"; // O redirigir a una página de error
                }

                // Obtener la lista de ejercicios (puede estar incluida en pet o separada)
                List<PhysicalExercise> physicalExerciseList = physicalExerciseService.getExercises(token, id);
                pet.setPhysicalExerciseList(physicalExerciseList); // Asegúrate de que Pet tenga este method

                // Agregar atributos al modelo
                model.addAttribute("pet", pet);              // Mascota completa
                model.addAttribute("newExercise", new PhysicalExercise()); // Objeto vacío para el formulario

                return "exercises"; // Nombre de la plantilla vaccines.html
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar las ejercicios: " + e.getMessage());
                model.addAttribute("pet", petService.getPetById(token, id)); // Asegúrate de incluir la mascota si es posible
                return "exercises"; // Mantenerse en la página con el error, no redirigir
            }
        }

        return "redirect:/mascotas"; // Redirigir si no está autenticado
    }

    @PostMapping("/pet/{id}/exercises/add")
    public String addExercise(@PathVariable Long id, @ModelAttribute("newExercise") PhysicalExercise newExercise, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                Pet pet = petService.getPetById(token, id);
                newExercise.setPet(pet);
                newExercise.setDuration(newExercise.getDuration() * 60); // Convertir minutos a segundos
                physicalExerciseService.saveExercise(token, newExercise, id);
                model.addAttribute("success", "Ejercicio añadido correctamente.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al añadir el ejercicio: " + e.getMessage());
            }
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            model.addAttribute("newExercise", new PhysicalExercise());
            return "exercises";
        }
        return "redirect:/mascotas";
    }

    @PostMapping("/pet/{id}/exercises/delete/{exerciseId}")
    public String deleteExercise(@PathVariable Long id, @PathVariable Long exerciseId, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                physicalExerciseService.deleteExerciseById(token, exerciseId);
                model.addAttribute("success", "Ejercicio eliminado correctamente.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al eliminar el ejercicio: " + e.getMessage());
            }
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            model.addAttribute("newExercise", new PhysicalExercise());
            return "exercises";
        }
        return "redirect:/mascotas";
    }

}
