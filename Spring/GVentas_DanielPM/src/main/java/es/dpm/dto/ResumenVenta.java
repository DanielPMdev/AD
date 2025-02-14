package es.dpm.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author danielpm.dev
 */
public class ResumenVenta {

    private LocalDate fechaVenta;
    private String nombreCliente;
    private String nombreEmpleado;
    private String nombreArticulo;
    private float precioCompra;
    private float iva;
    private float importeTotal;


    public ResumenVenta() {
    }

    public ResumenVenta(LocalDate fechaVenta, String nombreCliente, String nombreEmpleado,
                        String nombreArticulo, float precioCompra, float iva, float importeTotal) {
        this.fechaVenta = fechaVenta;
        this.nombreCliente = nombreCliente;
        this.nombreEmpleado = nombreEmpleado;
        this.nombreArticulo = nombreArticulo;
        this.precioCompra = precioCompra;
        this.iva = iva;
        this.importeTotal = importeTotal;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(float importeTotal) {
        this.importeTotal = importeTotal;
    }
}