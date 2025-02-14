package es.dpm.controladores;

// Controller (DepartamentoController.java)

import es.dpm.entities.Departamento;
import es.dpm.entities.Empleado;
import es.dpm.repositorios.DepartamentoRepository;
import es.dpm.repositorios.EmpleadoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;

    public DepartamentoController(DepartamentoRepository departamentoRepository, EmpleadoRepository empleadoRepository) {
        this.departamentoRepository = departamentoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    //departamentos/
    @GetMapping("/")
    public String listarDepartamentos(Model model) {
        List<Departamento> departamentos = departamentoRepository.findAll();
        model.addAttribute("departamentos", departamentos);
        return "listaDep";
    }

    //departamentos/nuevo
    @GetMapping("/nuevo")
    public String nuevoDepartamento(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "formularioDep";  //formularioDep.html
    }
    //departamentos/guardar
    @PostMapping("/guardar")
    public String guardarDepartamento(@ModelAttribute Departamento departamento) {
        departamentoRepository.save(departamento);
        return "redirect:/departamentos";
    }
    //departamentos/editar/1 (por ejemplo)
    @GetMapping("/editar/{id}")
    public String editarDepartamento(@PathVariable Long id, Model model) {
        Departamento departamento = departamentoRepository.findById(id).orElse(null);
        model.addAttribute("departamento", departamento);
        return "departamentos/formulario";
    }

    //departamentos/eliminar/1 (por ejemplo)
    @GetMapping("/eliminar/{id}")
    public String eliminarDepartamento(@PathVariable Long id) {
        departamentoRepository.deleteById(id);
        return "redirect:/departamentos";
    }
    //departamentos/empleados/1 (por ejemplo)
    @GetMapping("/empleados/{id}")
    public String listarEmpleados(@PathVariable Long id, Model model) {
        Departamento departamento = departamentoRepository.findById(id).orElse(null);
        if (departamento != null) {
            model.addAttribute("departamento", departamento);
            model.addAttribute("empleados", departamento.getListaEmpleados());
        }
        return "empleados/lista"; // Página para listar empleados
    }

    //departamentos/empleados/nuevo/1 (por ejemplo)
    @GetMapping("/empleados/nuevo/{id}")
    public String nuevoEmpleado(@PathVariable Long id, Model model) {
        Empleado empleado = new Empleado();
        Departamento departamento = departamentoRepository.findById(id).orElse(null);
        empleado.setDepartamento(departamento);
        model.addAttribute("empleado", empleado);
        return "empleados/formulario";
    }
    //departamentos/empleados/guardar
    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(@ModelAttribute Empleado empleado) {
        empleadoRepository.save(empleado);
        return "redirect:/departamentos/empleados/" + empleado.getDepartamento().getId();
    }

    // ... (otros métodos para editar y eliminar empleados)
}