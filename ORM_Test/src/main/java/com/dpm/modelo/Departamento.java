package com.dpm.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielpm.dev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DPM_DEPARTAMENTO")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPT")
    private long id;
    @Column(unique = true, nullable = false, name = "NOMBRE_DEPT") // Asegura que el nombre sea unico
    private String nombre;
    @Column(name = "LOC_DEP")
    private String localidad;

    @OneToMany(mappedBy = "departamento" , cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER) //EAGER PARA ACCEDER A LA LISTA DESDE FUERA DE LA PERSISTENCIA
    private List<Empleado> listaEmpleados = new ArrayList<>();

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

    //HAGO ESTO YA QUE HAY CONFLICTO CON EL TOSTRING DE LOMBOK Y LA LISTA EMPLEADOS
    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", localidad='" + localidad + '\'' +
                '}';
    }
}
