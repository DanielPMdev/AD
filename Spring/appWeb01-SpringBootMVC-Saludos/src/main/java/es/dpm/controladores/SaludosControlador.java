package es.dpm.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SaludosControlador {

    //http://localhost:9001/
    @RequestMapping({"/"})
    public String irSaludo(Model model){
        String miNombre = "Bautista R";
        String miLocalidad = "Miguel Esteban";
        model.addAttribute("nombre", miNombre);
        model.addAttribute("localidad", miLocalidad);
        return "saludo";  //saludo.html
    }

    //http://localhost:9001/saludo
    @GetMapping("/saludo")
    public String saludoModel(Model modelo) {
        String saludo = "Hola a todos. Os saluda SpringBoot MVC";
        modelo.addAttribute("mensaje", saludo);
        return "saludoMensaje";

    }

    //PARAMETROS:     url?nombre=valor
    // -----------------------------------------------
    //localhost:9001/saludoParametro?nombre=Pepe
    @GetMapping("/saludoParametro")
    public String saludoRequestParam(@RequestParam String nombre, Model modelo){
    String saludo = "Hola " + nombre;
        modelo.addAttribute("mensaje",saludo);
        return "saludoMensaje";
    }

    //@GetMapping("/saludoParametro")
//    public String saludoRequestParam(@RequestParam("nombre") String nombre,Model modelo){
//    String saludo = "Hola " + nombre;
//        modelo.addAttribute("mensaje",saludo);
//        return"saludoModel";
//    }

    //localhost:9001/saludoParametro?nombre=Pepe
//    @GetMapping("/saludoParametro")
//    public String saludoRequestParam(@RequestParam(name = "nombre", required = false, defaultValue = "Mundo") String nombre, Model modelo) {
//        String saludo = "Hola " + nombre;
//        modelo.addAttribute("mensaje", saludo);
//        return "saludoModel";
//    }


    //VARIABLE:    url/variable  (variable es un trozo de la url)
    //---------------------------------------------------------------------------------------
    //http://localhost:9001/saludoVariable/Pepe
    @GetMapping("/saludoVariable/{nombre}")
    public String saludoPathVariable(@PathVariable String nombre, Model modelo) {
        // public String saludoPathVariable(@PathVariable(name="nombre") String nombre, Model modelo){
        String saludo = "Hola " + nombre;
        modelo.addAttribute("mensaje", saludo);
        return "saludoMensaje";
    }



    //EJERCICIO-1. endpoint con tres parámetros.
    //localhost:9000/saludoParametros?nombre=Daniel&apellidos=Porras&localidad=Tresjuncos
    @GetMapping("/saludoParametros")
    public String saludoRequestParam(@RequestParam String nombre, @RequestParam String apellidos, @RequestParam String localidad, Model modelo) {
        String saludo = "Hola " + nombre + " " + apellidos + " vives en " + localidad;
        modelo.addAttribute("mensaje", saludo);
        return "saludoMensaje";
    }




    //EJERCICIO-2. endpoint con tres variables
    //http://localhost:9000/saludoVariables/Daniel/Porras/Tresjuncos
    @GetMapping("/saludoVariables/{nombre}/{apellidos}/{localidad}")
    public String saludoPathVariable(@PathVariable String nombre, @PathVariable String apellidos, @PathVariable String localidad, Model modelo) {
        String saludo = "Hola " + nombre + " " + apellidos + " vives en " + localidad;
        modelo.addAttribute("mensaje", saludo);
        return "saludoMensaje";
    }



}