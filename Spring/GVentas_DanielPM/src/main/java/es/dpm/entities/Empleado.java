package es.dpm.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * @author danielpm.dev
 */

//JPA
@Entity
@Table(name = "DMP_EMPLEADO")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "ID_EMP")
    private Long id;
    private String nombre;
    private float salario;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    //@JoinColumn(name = "id", foreignKey = @ForeignKey(name = "id"))
    private Departamento departamento;

    @Embedded
    private Direccion direccion;

    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER)
    private List<Venta> ventas;

    public Empleado(String nombre, float salario, LocalDate fechaNacimiento, Departamento departamento, Direccion direccion) {
        this.nombre = nombre;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
        this.departamento = departamento;
        this.direccion = direccion;
    }

    public Empleado() {

    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getSalario() {
        return salario;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", salario=" + salario +
                ", fechaNacimiento=" + fechaNacimiento +
                ", departamento=" + departamento +
                ", direccion=" + direccion +
                '}';
    }
}
