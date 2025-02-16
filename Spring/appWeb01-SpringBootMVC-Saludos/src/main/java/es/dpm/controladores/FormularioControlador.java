package es.dpm.controladores;

import es.dpm.modelo.Comida;
import es.dpm.modelo.Equipo;
import es.dpm.modelo.Hobby;
import es.dpm.modelo.Persona;
import es.dpm.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class FormularioControlador {

    private final Persona persona;
    private final PersonaRepository personaRepository;

    public FormularioControlador(PersonaRepository personaRepository, Persona persona) {
        this.personaRepository = personaRepository;
        this.persona = persona;
    }

    //http://localhost:9001/formulario  (por method GET)
    @GetMapping("/formulario")  //ir al formulario para la entrada de datos
    public String mostrarFormulario(Model model) {
        model.addAttribute("hobbies", Hobby.values());
        model.addAttribute("equipos", Equipo.values());
        model.addAttribute("comidas", Arrays.asList(Comida.values()));
        model.addAttribute("rPersona", persona);  //datos es  el objeto "vehículo" de transporte. (Va instanciado)
        return "formularioEntrada";
    }

    //http://localhost:9001/formulario   (por method POST)
    @PostMapping("/formulario") //simplemente pasamos la persona recibida a formularioSalida
    public String procesarFormulario(@ModelAttribute Persona persona, Model model) {
        // Realizar acciones con los datos,

        model.addAttribute("rPersona", persona);
        personaRepository.save(persona);

        return "formularioSalida";
    }

    @GetMapping("personas/list")
    public String mostrarPersonas(Model model) {
        model.addAttribute("rPersonas", personaRepository.findAll());
        return "personaList";
    }



}
