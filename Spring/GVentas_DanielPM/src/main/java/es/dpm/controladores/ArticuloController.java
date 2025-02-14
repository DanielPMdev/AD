package es.dpm.controladores;

import es.dpm.entities.Articulo;
import es.dpm.repositorios.ArticuloRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticuloController {
    
    private final ArticuloRepository articuloRepository;

    public ArticuloController(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    @GetMapping("/articulos")
    public String findAllArticulos(Model model) {
        model.addAttribute("articulos", articuloRepository.findAll());
        return "articulo-list";
    }

    @GetMapping("/articulos/{id}")
    public String findArticulosByID(Model model, @PathVariable Long id) {
        if (articuloRepository.findById(id).isPresent()) {
            model.addAttribute("articulo", articuloRepository.findById(id).get());
            return "articulo-view";
        }
        return "articulo-view";
    }

    @GetMapping("articulos/new")
    public String getEmptyForm(Model model) {
        model.addAttribute("articulo", new Articulo());
        return "articulo-form";
    }

    @GetMapping("articulos/edit/{id}")
    public String getFormEdit(Model model, @PathVariable Long id) {
        if (articuloRepository.existsById(id)) {
            articuloRepository.findById(id).ifPresent(v ->
                    model.addAttribute("articulo", v));
            return "articulo-form";
        } else {
            return "redirect:/articulos/new";
        }
    }
    
    @PostMapping("/articulos")
    public String createArticulos(@ModelAttribute Articulo articulo) {
        if (articulo.getId() != null){
            //Actualización
            articuloRepository.findById(articulo.getId()).ifPresent(a -> {
                //SETTEAR CON LOS ATRIBUTOS NUEVOS
                a.setNombre(articulo.getNombre());
                a.setPrecioCompra(articulo.getPrecioCompra());
                articuloRepository.save(a);
            });
        } else {
            // Creación de articulo
            articuloRepository.save(articulo);
        }

        return "redirect:/articulos"; //Mover a la lista de articulos cuando ya se haya creado
    }

    @GetMapping("/articulos/delete/{id}")
    public String deleteArticulosById(@PathVariable Long id) {
        if(articuloRepository.existsById(id)){
            articuloRepository.deleteById(id);
        }
        return "redirect:/articulos";
    }
}
