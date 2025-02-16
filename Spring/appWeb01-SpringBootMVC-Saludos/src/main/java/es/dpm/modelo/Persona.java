package es.dpm.modelo;


import es.dpm.util.ComidaListConverter;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String localidad;
    private Integer edad;
    @Enumerated(EnumType.STRING) //GUARDA EL NOMBRE DEL ENUMERATED
    private Hobby hobby;
    private String comentario;
    //@Enumerated(EnumType.STRING)
    @Convert(converter = ComidaListConverter.class) // Usa el convertidor
    private List<Comida> comidas;
    private String equipo;

    public Persona() {
    }

    public Persona(String nombre, String apellidos, String telefono, String email, String localidad, Integer edad, Hobby hobby, String comentario, List<Comida> comidas, String equipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.localidad = localidad;
        this.edad = edad;
        this.hobby = hobby;
        this.comentario = comentario;
        this.comidas = comidas;
        this.equipo = equipo;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<Comida> getComidas() {
        return comidas;
    }

    public void setComidas(List<Comida> comidas) {
        this.comidas = comidas;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }


}
