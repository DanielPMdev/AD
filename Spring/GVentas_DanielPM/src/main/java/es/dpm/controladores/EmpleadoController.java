package es.dpm.controladores;

import es.dpm.entities.Empleado;
import es.dpm.repositorios.DepartamentoRepository;
import es.dpm.repositorios.EmpleadoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoController(EmpleadoRepository empleadoRepository, DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @GetMapping("/empleados")
    public String findAllEmpleados(Model model) {
        model.addAttribute("empleados", empleadoRepository.findAll());
        return "empleado-list";
    }

    @GetMapping("/empleados/{id}")
    public String findEmpleadosByID(Model model, @PathVariable Long id) {
        if (empleadoRepository.findById(id).isPresent()) {
            model.addAttribute("empleado", empleadoRepository.findById(id).get());
            return "empleado-view";
        }
        return "empleado-view";
    }

    @GetMapping("empleados/new")
    public String getEmptyForm(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("departamentos", departamentoRepository.findAll());
        return "empleado-form";
    }

    @GetMapping("empleados/edit/{id}")
    public String getFormEdit(Model model, @PathVariable Long id) {
        if (empleadoRepository.existsById(id)) {
            empleadoRepository.findById(id).ifPresent(e ->
                    model.addAttribute("empleado", e));
            model.addAttribute("departamentos", departamentoRepository.findAll());
            return "empleado-form";
        } else {
            return "redirect:/empleados/new";
        }
    }

    @PostMapping("/empleados")
    public String createEmpleados(@ModelAttribute Empleado empleado) {
        if (empleado.getId() != null){
            //Actualización
            empleadoRepository.findById(empleado.getId()).ifPresent(e -> {
                //SETTEAR CON LOS ATRIBUTOS NUEVOS
                e.setNombre(empleado.getNombre());
                e.setDepartamento(empleado.getDepartamento());
                e.setSalario(empleado.getSalario());
                e.setFechaNacimiento(empleado.getFechaNacimiento());
                e.setDireccion(empleado.getDireccion());
                empleadoRepository.save(e);
            });
        } else {
            // Creación de empleado
            empleadoRepository.save(empleado);
        }

        return "redirect:/empleados"; //Mover a la lista de empleados cuando ya se haya creado
    }

    @GetMapping("/empleados/delete/{id}")
    public String deleteEmpleadosById(@PathVariable Long id) {
        if(empleadoRepository.existsById(id)){
            empleadoRepository.deleteById(id);
        }
        return "redirect:/empleados";
    }
}