package com.dpm.controller;

import com.dpm.model.Owner;
import com.dpm.service.AuthService;
import com.dpm.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author danielpm.dev
 */
@Controller
public class OwnerController {

    private final OwnerService ownerService;
    private final AuthService authService;

    public OwnerController(OwnerService ownerService, AuthService authService) {
        this.ownerService = ownerService;
        this.authService = authService;
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                Owner owner = ownerService.getAuthenticatedUser(token);
                model.addAttribute("owner", owner);
                return "perfil"; // Devuelve la plantilla perfil.html
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar el perfil: " + e.getMessage());
                return "perfil";
            }
        } else {
            return "redirect:/login"; // Si no está autenticado, redirige a login
        }
    }

    // Actualizar el email
    @PostMapping("/perfil/update")
    public String updateEmail(@RequestParam("email") String email, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        //model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                ownerService.updateEmail(token, email);
                redirectAttributes.addFlashAttribute("success", "Email actualizado correctamente.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar el email: " + e.getMessage());
            }
        }

        return "redirect:/perfil";
    }

    // Actualizar la imagen
    @PostMapping("/perfil/update-image")
    public String updateImage(@RequestParam("imageUrl") String imageUrl, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        //model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                ownerService.updateImage(token, imageUrl);
                redirectAttributes.addFlashAttribute("success", "Imagen actualizada correctamente.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar la imagen: " + e.getMessage());
            }
        }

        return "redirect:/perfil";
    }

    @PostMapping("/perfil/change-password")
    public String changePassword(@RequestParam Long ownerId, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        try {
            authService.resetPassword(ownerId, newPassword); // Pasamos el token
            model.addAttribute("success", "Contraseña actualizada correctamente.");
            Owner owner = ownerService.getAuthenticatedUser(token);
            model.addAttribute("owner", owner);
            return "perfil";
        } catch (Exception e) {
            model.addAttribute("modalError", "Error al cambiar la contraseña: " + e.getMessage());
            Owner owner = ownerService.getAuthenticatedUser(token);
            model.addAttribute("owner", owner);
            return "perfil";
        }
    }

    // Eliminar el perfil
    @PostMapping("/perfil/delete")
    public String deleteProfile(HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        //model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                ownerService.deleteOwner(token);
                return "redirect:/logout"; // Redirige al logout tras eliminar
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al eliminar el perfil: " + e.getMessage());
            }
        }

        return "redirect:/perfil";
    }
}