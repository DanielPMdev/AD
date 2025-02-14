package es.dpm.entities;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Venta> ventas;

    public Cliente() {

    }

    public Cliente(String nombre, String telefono, List<Venta> ventas) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.ventas = ventas;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setId(Long idCli) {
        this.id = idCli;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCli=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", ventas=" + ventas +
                '}';
    }
}
