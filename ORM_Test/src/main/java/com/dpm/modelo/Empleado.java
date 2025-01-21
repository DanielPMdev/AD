package com.dpm.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

/**
 * @author danielpm.dev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "DMP_EMPLEADO")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "ID_EMP")
    private int id;
    private String nombre;
    private float salario;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    //@JoinColumn(name = "id", foreignKey = @ForeignKey(name = "id"))
    private Departamento departamento;

    @OneToOne (cascade = {CascadeType.ALL})
    private Direccion direccion;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Venta> ventas;

    public Empleado(String nombre, float salario, LocalDate fechaNacimiento, Departamento departamento, Direccion direccion) {
        this.nombre = nombre;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
        this.departamento = departamento;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", salario=" + salario +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
