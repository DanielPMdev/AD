package com.dpm;

import com.dpm.models.Estado;
import com.dpm.models.Persona;
import com.dpm.models.Tarea;
import com.dpm.repositorys.PersonaRepository;
import com.dpm.repositorys.TareaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        TareaRepository tareaRepo = context.getBean(TareaRepository.class);
        PersonaRepository personaRepo = context.getBean(PersonaRepository.class);

        // Crear las personas primero
        Persona persona1 = new Persona();
        persona1.setNombre("Daniel Porras");
        persona1.setCorreo("daniel@email.com");

        Persona persona2 = new Persona();
        persona2.setNombre("Bautista Profe");
        persona2.setCorreo("bautista@email.com");

        // Crear las tareas
        List<Tarea> tareas = List.of(
                new Tarea("Completar informe", Estado.PENDIENTE, persona1),
                new Tarea("Revisar presentación", Estado.PENDIENTE, persona1),
                new Tarea("Preparar reunión", Estado.PENDIENTE, persona1),
                new Tarea("Enviar email", Estado.PENDIENTE, persona2),
                new Tarea("Actualizar documentación", Estado.PENDIENTE, persona2)
        );


        personaRepo.saveAll(List.of(persona1, persona2));
        tareaRepo.saveAll(tareas);
    }


}
