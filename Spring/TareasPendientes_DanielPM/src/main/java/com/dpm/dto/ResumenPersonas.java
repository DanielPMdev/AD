package com.dpm.dto;

/**
 * @author danielpm.dev
 */
public class ResumenPersonas {

    private String nombre;
    private long totalTareas;
    private long tareasPendientes;
    private long tareasEnDesarrollo;
    private long tareasCompletadas;

    // Constructor para la consulta JPQL
    public ResumenPersonas(String nombre, Long totalTareas, Long tareasPendientes,
                            Long tareasEnDesarrollo, Long tareasCompletadas) {
        this.nombre = nombre;
        this.totalTareas = totalTareas != null ? totalTareas : 0;
        this.tareasPendientes = tareasPendientes != null ? tareasPendientes : 0;
        this.tareasEnDesarrollo = tareasEnDesarrollo != null ? tareasEnDesarrollo : 0;
        this.tareasCompletadas = tareasCompletadas != null ? tareasCompletadas : 0;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public long getTotalTareas() {
        return totalTareas;
    }

    public long getTareasPendientes() {
        return tareasPendientes;
    }

    public long getTareasEnDesarrollo() {
        return tareasEnDesarrollo;
    }

    public long getTareasCompletadas() {
        return tareasCompletadas;
    }

    // Opcional: toString
    @Override
    public String toString() {
        return "PersonaTareasDTO{" +
                "nombre='" + nombre + '\'' +
                ", totalTareas=" + totalTareas +
                ", tareasPendientes=" + tareasPendientes +
                ", tareasEnDesarrollo=" + tareasEnDesarrollo +
                ", tareasCompletadas=" + tareasCompletadas +
                '}';
    }

}
