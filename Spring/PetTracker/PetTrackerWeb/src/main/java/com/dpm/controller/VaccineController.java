package com.dpm.controller;

import com.dpm.model.Pet;
import com.dpm.model.Vaccine;
import com.dpm.service.OwnerService;
import com.dpm.service.PetService;
import com.dpm.service.VaccineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.dpm.service.OwnerService.getOwnerIdFromToken;

/**
 * @author danielpm.dev
 */
@Controller
public class VaccineController {

    private final VaccineService vaccineService;
    private final PetService petService;
    //private final OwnerService ownerService;

    public VaccineController(PetService petService, VaccineService vaccineService) {
        this.petService = petService;
        this.vaccineService = vaccineService;
    }

    @GetMapping("/pet/{id}/vaccines")
    public String showVaccines(@PathVariable Long id, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                // Obtener la mascota y sus vacunas
                Pet pet = petService.getPetById(token, id); // Asumiendo que tienes un servicio para esto
                if (pet == null) {
                    model.addAttribute("error", "Mascota no encontrada.");
                    return "mascotas"; // O redirigir a una página de error
                }

                // Obtener la lista de vacunas (puede estar incluida en pet o separada)
                List<Vaccine> vaccineList = vaccineService.getVaccines(token, id);
                pet.setVaccineList(vaccineList); // Asegúrate de que Pet tenga este method

                // Agregar atributos al modelo
                model.addAttribute("pet", pet);              // Mascota completa
                model.addAttribute("newVaccine", new Vaccine()); // Objeto vacío para el formulario

                return "vaccines"; // Nombre de la plantilla vaccines.html
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar las vacunas: " + e.getMessage());
                model.addAttribute("pet", petService.getPetById(token, id)); // Asegúrate de incluir la mascota si es posible
                return "vaccines"; // Mantenerse en la página con el error, no redirigir
            }
        }

        return "redirect:/mascotas"; // Redirigir si no está autenticado
    }

    @PostMapping("/pet/{id}/vaccines/add")
    public String addVaccine(@PathVariable Long id, @ModelAttribute("newVaccine") Vaccine newVaccine, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                Pet pet = petService.getPetById(token, id);
                newVaccine.setPet(pet); // Asociar la vacuna a la mascota
                vaccineService.saveVaccine(token, newVaccine, pet.getId()); // Guardar la nueva vacuna
                model.addAttribute("success", "Vacuna añadida correctamente.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al añadir la vacuna: " + e.getMessage());
            }
            // Recargar datos para mostrar la lista actualizada
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            model.addAttribute("newVaccine", new Vaccine());
            return "vaccines";
        }

        return "redirect:/mascotas";
    }

    @PostMapping("/pet/{id}/vaccines/delete/{vaccineId}")
    public String deleteVaccine(@PathVariable Long id, @PathVariable Long vaccineId, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                vaccineService.deleteVaccineById(token, vaccineId);
                model.addAttribute("success", "Vacuna borrada correctamente.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al borrar la vacuna: " + e.getMessage());
            }
            // Recargar datos para mostrar la lista actualizada
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            model.addAttribute("newVaccine", new Vaccine());
            return "vaccines";
        }

        return "redirect:/mascotas";
    }

}
