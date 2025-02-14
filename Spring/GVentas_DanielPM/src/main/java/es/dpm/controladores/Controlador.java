package es.dpm.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Controlador {


    //@RequestMapping({"/saludo"})
    @GetMapping({"/saludo"})
    public String irSaludo(Model modelo){
        String miNombre = "DanielPM";
        modelo.addAttribute("nombre", miNombre);
        modelo.addAttribute("localidad", "Tresjuncos");
        return "saludo";  //saludo.html
    }
    //@RequestMapping({"/index","/"})
    @GetMapping({"/index", "/"})
    public String irIndex(){
        return "index";  //index.html
    }

}
