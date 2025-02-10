package es.dpm.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class InicioControlador {
    @GetMapping("/inicio")
    public String mostrarUrls(Model model) {
        List<String> urls = Arrays.asList(
                "http://localhost:9000/",
                "http://localhost:9000/saludo",
                "http://localhost:9000/saludoParametro?nombre=Pepe",
                "http://localhost:9000/saludoVariable/Pepe",
                "http://localhost:9000/saludoParametros?nombre=Bautisa&apellidos=Ramírez&localidad=M.Esteban",
                "http://localhost:9000/saludoVariables/Bautista/Ramirez/MEsteban",
                "http://localhost:9000/formulario ",
                "http://localhost:9000/personas/list",
                "https://www.google.es/");

        model.addAttribute("urls", urls);
        return "inicio"; // Nombre del archivo HTML (sin extensión)
    }
}