package com.dpm.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idArt;
    private String nombre;
    private float precioCompra;

    @OneToOne(mappedBy = "articulo", cascade = CascadeType.ALL)
    private Venta venta;
}