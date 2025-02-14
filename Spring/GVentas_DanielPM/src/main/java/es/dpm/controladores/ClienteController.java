package es.dpm.controladores;

import es.dpm.entities.Cliente;
import es.dpm.repositorios.ClienteRepository;
import es.dpm.repositorios.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/clientes")
    public String findAllClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "cliente-list";
    }

    @GetMapping("/clientes/{id}")
    public String findClientesByID(Model model, @PathVariable Long id) {
        if (clienteRepository.findById(id).isPresent()) {
            model.addAttribute("cliente", clienteRepository.findById(id).get());
            return "cliente-view";
        }
        return "cliente-view";
    }

    @GetMapping("clientes/new")
    public String getEmptyForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente-form";
    }

    @GetMapping("clientes/edit/{id}")
    public String getFormEdit(Model model, @PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.findById(id).ifPresent(v ->
                    model.addAttribute("cliente", v));
            return "cliente-form";
        } else {
            return "redirect:/clientes/new";
        }
    }

    @PostMapping("/clientes")
    public String createClientes(@ModelAttribute Cliente cliente) {
        if (cliente.getId() != null){
            //Actualización
            clienteRepository.findById(cliente.getId()).ifPresent(c -> {
                //SETTEAR CON LOS ATRIBUTOS NUEVOS
               c.setNombre(cliente.getNombre());
               c.setTelefono(cliente.getTelefono());
               clienteRepository.save(c);
            });
        } else {
            // Creación de cliente
            clienteRepository.save(cliente);
        }

        return "redirect:/clientes"; //Mover a la lista de clientes cuando ya se haya creado
    }

    @GetMapping("/clientes/delete/{id}")
    public String deleteClientesById(@PathVariable Long id) {
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
        }
        return "redirect:/clientes";
    }
}
