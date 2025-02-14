package es.dpm.entities;

import jakarta.persistence.*;

//JPA
@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private float precioCompra;

    @OneToOne(mappedBy = "articulo")
    private Venta venta;

    public Articulo() {
    }

    public Articulo(String nombre, float precioCompra) {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
    }

    public Articulo(String nombre, float precioCompra, Venta venta) {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.venta = venta;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "idArt=" + id +
                ", nombre='" + nombre + '\'' +
                ", precioCompra=" + precioCompra +
                ", venta=" + venta +
                '}';
    }
}