package com.dpm.models;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author danielpm.dev
 */
@Entity
@Table(name = "T_PERSONA")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;

    @OneToMany(mappedBy = "persona")
    private List<Tarea> listaTareas;

    public Persona() {
    }

    public Persona(String nombre, String correo, List<Tarea> listaTareas) {
        this.nombre = nombre;
        this.correo = correo;
        this.listaTareas = listaTareas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(List<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }
}
