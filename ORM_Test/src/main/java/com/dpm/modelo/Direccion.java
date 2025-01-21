package com.dpm.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author danielpm.dev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//------------------------------------
@Entity
@Table(name = "DPM_DIRECCION")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String direccion;
    private String localidad;
    @Column(name = "codigo_postal")
    private String cp;

    @OneToOne(mappedBy = "direccion")
    private Empleado empleado;

    public Direccion(String direccion, String localidad, String cp, Empleado empleado) {
        this.direccion = direccion;
        this.localidad = localidad;
        this.cp = cp;
        this.empleado = empleado;
    }
}
