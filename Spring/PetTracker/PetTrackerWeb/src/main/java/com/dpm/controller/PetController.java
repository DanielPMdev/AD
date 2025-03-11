package com.dpm.controller;

import com.dpm.model.Owner;
import com.dpm.model.Pet;
import com.dpm.model.PhysicalExercise;
import com.dpm.model.Vaccine;
import com.dpm.service.ApiService;
import com.dpm.service.OwnerService;
import com.dpm.service.PetService;
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
public class PetController {

    private final PetService petService;
    private final OwnerService ownerService;

    public PetController(PetService petService, OwnerService ownerService) {
        this.petService = petService;
        this.ownerService = ownerService;
    }

    @GetMapping("/mascotas")
    public String showPets(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                List<Pet> pets = petService.getPets(token);
//                Owner owner = ownerService.getAuthenticatedUser(token);
//                List<Pet> pets = owner.getPetList(); // Asumo que petList está en Owner
                model.addAttribute("pets", pets);
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar las mascotas: " + e.getMessage());
            }
        }

        return "mascotas"; // Nombre de la plantilla
    }

    @GetMapping("/pet/{id}")
    public String petDetails(@PathVariable Long id, @RequestParam(required = false) Boolean editing, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");

        if (token == null) {
            model.addAttribute("error", "No se encontró un token de autenticación válido");
            return "error";
        }

        try {
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            model.addAttribute("editing", editing != null && editing);
            return "pet-details";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al cargar los detalles de la mascota: " + e.getMessage());
            return "pet-details";
        }
    }

    @GetMapping("/pet/add")
    public String petForm() {
        //Pet pet = apiService.getPetById(id);
        //model.addAttribute("pet", pet);
        return "pet-form";
    }

    // Guardar la nueva mascota
    @PostMapping("/pet/add")
    public String savePet(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam("species") String species,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam("sex") char sex,
            @RequestParam(value = "weight", required = false) Double weight,
            @RequestParam(value = "urlImage", required = false) String urlImage,
            @RequestParam(value = "diseaseList", required = false) String diseaseList, // Nuevo campo
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();

        if (isAuthenticated) {
            try {
                Pet pet = new Pet();
                pet.setName(name);
                pet.setDescription(description);
                pet.setAge(age);
                pet.setSpecies(species);
                pet.setBreed(breed);
                pet.setSex(sex);
                pet.setWeight(weight);
                pet.setUrlImage(urlImage);
                // Manejar diseaseList como una lista, asumiendo que viene como una cadena separada por comas
                if (diseaseList != null && !diseaseList.isEmpty()) {
                    pet.setDiseaseList(Arrays.asList(diseaseList.split(",")));
                } else {
                    pet.setDiseaseList(Collections.singletonList("None")); // Valor por defecto como en el JSON
                }
                // Obtener el ownerId desde el token
                Long ownerId = getOwnerIdFromToken(token);
                petService.addPet(token, pet, ownerId); // Pasar ownerId por separado
                redirectAttributes.addFlashAttribute("success", "Mascota añadida correctamente.");
                return "redirect:/mascotas";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al añadir la mascota: " + e.getMessage());
                return "redirect:/pet/add";
            }
        }
        return "redirect:/mascotas";
    }

    @PostMapping("/pet/{id}")
    public String updatePet(@PathVariable Long id, @ModelAttribute Pet pet, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        if (token == null) {
            model.addAttribute("error", "No se encontró un token de autenticación válido");
            return "error";
        }

        try {
            pet.setId(id); // Asegúrate de que el ID se mantenga
            Long ownerId = getOwnerIdFromToken(token);

            // Actualiza la mascota
            petService.updatePet(token, pet, ownerId);

            // Recarga la mascota completa desde el servicio
            Pet updatedPet = petService.getPetById(token, id);

            model.addAttribute("success", "Mascota actualizada con éxito");
            model.addAttribute("pet", updatedPet);
            model.addAttribute("editing", false);
            return "pet-details";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al guardar la mascota: " + e.getMessage());
            model.addAttribute("pet", pet);
            model.addAttribute("editing", true);
            return "pet-details";
        }
    }

    @PostMapping("/pet/delete/{id}")
    public String deletePet(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        redirectAttributes.addFlashAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                System.out.println("Eliminando mascota con ID: " + id); // Depuración
                petService.deletePetById(token, id);
                redirectAttributes.addFlashAttribute("success", "Mascota eliminada correctamente.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al eliminar la mascota: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Debes estar autenticado para eliminar una mascota.");
        }
        return "redirect:/mascotas";
    }
}


//    @GetMapping("/login")
//    public String loginForm() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password, Model model) {
//        String token = apiService.authenticate(username, password);
//        // Almacena el token (puedes usar sesión o una variable global)
//        model.addAttribute("message", "Login exitoso");
//        return "redirect:/";
//    }
