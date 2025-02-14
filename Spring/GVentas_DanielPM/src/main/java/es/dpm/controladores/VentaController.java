package es.dpm.controladores;

import es.dpm.dto.ResumenVenta;
import es.dpm.entities.Articulo;
import es.dpm.entities.Cliente;
import es.dpm.entities.Empleado;
import es.dpm.entities.Venta;
import es.dpm.repositorios.ArticuloRepository;
import es.dpm.repositorios.ClienteRepository;
import es.dpm.repositorios.EmpleadoRepository;
import es.dpm.repositorios.VentaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author danielpm.dev
 */
@Controller
public class VentaController {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ArticuloRepository articuloRepository;
    private final EmpleadoRepository empleadoRepository;

    public VentaController(VentaRepository ventaRepository, ClienteRepository clienteRepository, ArticuloRepository articuloRepository, EmpleadoRepository empleadoRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.articuloRepository = articuloRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping("/resumen")
    public String listaResumenVentas(Model model) {
        List<ResumenVenta> resumenVentas = ventaRepository.obtenerResumenVentas();
        model.addAttribute("resumenV", resumenVentas);
        return "resumenVentas";
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/ventas";
    }

    @GetMapping("/ventas")
    public String findAllVentas(Model model) {
        model.addAttribute("ventas", ventaRepository.findAll());
        return "venta-list";
    }

    @GetMapping("/ventas/{id}")
    public String findVentasByID(Model model, @PathVariable Long id) {
        if (ventaRepository.findById(id).isPresent()) {
            model.addAttribute("venta", ventaRepository.findById(id).get());
            return "venta-view";
        }
        return "venta-view";
    }

    @GetMapping("ventas/new")
    public String getEmptyForm(Model model) {
        model.addAttribute("venta", new Venta());
        cargarListas(model);
        return "venta-form";
    }

    @GetMapping("ventas/edit/{id}")
    public String getFormEdit(Model model, @PathVariable Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.findById(id).ifPresent(v -> {
                System.out.println(v.getFechaVenta());
                model.addAttribute("venta", v);
            });

            cargarListas(model);
            return "venta-form";
        } else {
            return "redirect:/ventas/new";
        }
    }

    private void cargarListas(Model model) {
        model.addAttribute("empleados", empleadoRepository.findAll());
        model.addAttribute("articulos", articuloRepository.findArticulosSinVenta());
        model.addAttribute("clientes", clienteRepository.findAll());
    }

    @PostMapping("/ventas")
    public String createVentas(@ModelAttribute Venta venta) {
        if (venta.getId() != null){
            //Actualización
            ventaRepository.findById(venta.getId()).ifPresent(v -> {
                Articulo art = articuloRepository.findById(venta.getArticulo().getId()).orElse(null);
                Empleado emp = empleadoRepository.findById(venta.getEmpleado().getId()).orElse(null);
                Cliente cliente = clienteRepository.findById(venta.getCliente().getId()).orElse(null);

                if (art != null) {
                    //SETTEAR CON LOS ATRIBUTOS NUEVOS
                    v.setEmpleado(emp);
                    v.setArticulo(art);
                    v.setCliente(cliente);
                    v.setFechaVenta(venta.getFechaVenta());
                    v.setPrecioVenta(venta.getPrecioVenta());
                    ventaRepository.save(v);
                }
            });
        } else {
            // Creación de venta
            Articulo articulo = articuloRepository.findById(venta.getArticulo().getId()).orElse(null);
            if(articulo != null) {
                venta.setArticulo(articulo);
                ventaRepository.save(venta);
            }
        }

        return "redirect:/ventas"; //Mover a la lista de ventas cuando ya se haya creado
    }

    @GetMapping("/ventas/delete/{id}")
    public String deleteVentasById(@PathVariable Long id) {
        if(empleadoRepository.existsById(id)){
            empleadoRepository.deleteById(id);
        }
        return "redirect:/ventas";
    }
}
