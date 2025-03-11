package com.dpm.controller;

import com.dpm.service.ApiService;
import com.dpm.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author danielpm.dev
 */
@Controller
public class AuthController {

    //Cambiar por el Servicio Correspondiente
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);
        return "register"; // Muestra el formulario de registro
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model, HttpSession session) {
        try {
            // Aquí llamas al servicio para registrar al usuario
            authService.register(username, password, email);
            // Si el registro es exitoso, podrías iniciar sesión automáticamente o redirigir a login
            String token = authService.authenticate(username, password); // Autenticar tras registro
            session.setAttribute("jwtToken", token);
            return "redirect:/"; // Redirige a la página principal
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrarse: " + e.getMessage());
            model.addAttribute("authenticated", false);
            return "register"; // Vuelve al formulario con el mensaje de error
        }
    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);
        return "login"; // Devuelve la plantilla login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            String token = authService.authenticate(username, password);
            session.setAttribute("jwtToken", token);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Credenciales inválidas");
            model.addAttribute("authenticated", false);
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        String token = (String) session.getAttribute("jwtToken");
        if (token != null && !token.isEmpty()) {
            try {
                authService.logout(token); // Llama al servicio para invalidar el token en la API
                session.invalidate(); // Invalida la sesión local
                return "redirect:/login?logout";
            } catch (Exception e) {
                return "redirect:/perfil?error=Error al cerrar sesión: " + e.getMessage();
            }
        }
        // Si no hay token, simplemente invalida la sesión y redirige
        session.invalidate();
        return "redirect:/login";
    }

    //CAMBIOS DE CONTRASEÑA

    @GetMapping("/cambiarContraseña")
    public String showForgotPasswordForm() {
        return "cambiar-contraseña";
    }

    @PostMapping("/cambiarContraseña")
    public String requestPasswordReset(@RequestParam String username, Model model) {
        try {
            Long ownerId = authService.getOwnerIdByUsername(username); // Obtener el ID del usuario
            model.addAttribute("ownerId", ownerId);
            return "cambiar-contraseña-form";
        } catch (Exception e) {
            model.addAttribute("error", "Usuario no encontrado o error: " + e.getMessage());
            return "cambiar-contraseña";
        }
    }

    @PostMapping("/cambiarContraseña/reset")
    public String resetPassword(@RequestParam Long ownerId, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            model.addAttribute("ownerId", ownerId);
            return "cambiar-contraseña-form";
        }
        try {
            authService.resetPassword(ownerId, newPassword);
            model.addAttribute("success", "Contraseña actualizada correctamente. Inicia sesión con tu nueva contraseña.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cambiar la contraseña: " + e.getMessage());
            model.addAttribute("ownerId", ownerId);
            return "cambiar-contraseña-form";
        }
    }
}

/*
@Controller
public class AuthController {

    private final ApiService apiService;

    public AuthController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            String token = apiService.authenticate(username, password);
            session.setAttribute("jwtToken", token);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Credenciales inválidas");
            model.addAttribute("authenticated", false); // Asegura que no esté autenticado en caso de error
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Ejemplo para otras rutas como /mascotas o /perfil
    @GetMapping("/mascotas")
    public String mascotas(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);
        return "mascotas"; // Asume que tienes una plantilla mascotas.html
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);
        return "perfil"; // Asume que tienes una plantilla perfil.html
    }
}
 */