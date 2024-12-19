package com.dpm.modelo;

/**
 * @author danielpm.dev
 */
public class Titulo {
    private String tipo;
    private int cantidad;

    public Titulo() {
    }

    public Titulo(String tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

