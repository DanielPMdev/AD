package es.dpm.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielpm.dev
 */

@Entity
@Table(name = "DPM_DEPARTAMENTO")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPT")
    private Long id;
    @Column(unique = true, nullable = false, name = "NOMBRE_DEPT") // Asegura que el nombre sea unico
    private String nombre;
    @Column(name = "LOC_DEP")
    private String localidad;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER) //EAGER PARA ACCEDER A LA LISTA DESDE FUERA DE LA PERSISTENCIA
    private List<Empleado> listaEmpleados = new ArrayList<>();

    public Departamento() {

    }

    public Departamento(Long id, String nombre, String localidad, List<Empleado> listaEmpleados) {
        this.id = id;
        this.nombre = nombre;
        this.localidad = localidad;
        this.listaEmpleados = listaEmpleados;
    }

    public Departamento(String nombre, String localidad, List<Empleado> listaEmpleados) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.listaEmpleados = listaEmpleados;
    }

    public void addEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }
        if (!listaEmpleados.contains(empleado)) {
            listaEmpleados.add(empleado); // Añadir a la lista del departamento
            empleado.setDepartamento(this); // Decir que este empleado pertenece a este departamento
        }
    }

    public void removeEmpleado(Empleado empleado) {
        if (empleado != null && listaEmpleados.contains(empleado)) {
            listaEmpleados.remove(empleado); // Eliminar de la lista del departamento
            empleado.setDepartamento(null);  // Romper la relación inversa
        }
    }

    public Departamento(long id, String nombre, String localidad) {
        this.id = id;
        this.nombre = nombre;
        this.localidad = localidad;
    }

    public Departamento(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", localidad='" + localidad + '\'' +
                '}';
    }
}
