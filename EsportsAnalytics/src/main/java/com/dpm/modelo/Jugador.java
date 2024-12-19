package com.dpm.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author danielpm.dev
 */
public class Jugador {
    private int _id;
    private String nombre;
    private String posicion;
    private String nacionalidad;
    private Estadisticas estadisticas;
    private int id_equipo;

    public Jugador() {
    }

    public Jugador(int id, String nombre, String posicion, String nacionalidad, Estadisticas estadisticas, int idEquipo) {
        this._id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.nacionalidad = nacionalidad;
        this.estadisticas = estadisticas;
        this.id_equipo = idEquipo;
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

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Estadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    public int getIdEquipo() {
        return id_equipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.id_equipo = idEquipo;
    }
}
