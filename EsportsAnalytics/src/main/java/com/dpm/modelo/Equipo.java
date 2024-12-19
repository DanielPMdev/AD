package com.dpm.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author danielpm.dev
 */
public class Equipo {
    private int _id;
    private String nombre;
    private String region;
    private int ano_fundacion;
    private List<Titulo> titulos;
    private String entrenador;
    private List<Integer> jugadores;

    public Equipo() {
    }

    public Equipo(int id, String nombre, String region, int anoFundacion, List<Titulo> titulos, String entrenador, List<Integer> jugadores) {
        this._id = id;
        this.nombre = nombre;
        this.region = region;
        this.ano_fundacion = anoFundacion;
        this.titulos = titulos;
        this.entrenador = entrenador;
        this.jugadores = jugadores;
    }

    // Getters y Setters
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getAnoFundacion() {
        return ano_fundacion;
    }

    public void setAnoFundacion(int anoFundacion) {
        this.ano_fundacion = Math.max(anoFundacion, 1972);  // Asigna el valor de 1972 si el valor es menor que 1972
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public List<Integer> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Integer> jugadores) {
        this.jugadores = jugadores;
    }
}