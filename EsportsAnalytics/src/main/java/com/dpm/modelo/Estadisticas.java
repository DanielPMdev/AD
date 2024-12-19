package com.dpm.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author danielpm.dev
 */
public class Estadisticas {
    private double kda;
    private double cs_por_minuto;
    private int participacion_kill; // Representación numérica para cálculos
    private String participacionKillFormatted; // Representación formateada para visualización

    // Constructor vacío
    public Estadisticas() {
    }

    // Constructor con todos los argumentos
    public Estadisticas(double kda, double csPorMinuto, int participacionKill) {
        this.kda = kda;
        this.cs_por_minuto = csPorMinuto;
        this.participacion_kill = participacionKill;
        this.participacionKillFormatted = participacionKill + "%"; // Sincroniza el formateado
    }

    // Getters y Setters
    public double getKda() {
        return kda;
    }

    public void setKda(double kda) {
        if (kda < 0) {
            this.kda = 0; // Si kda es menor que 0, se asigna 0
        } else {
            this.kda = kda; // Si kda es mayor o igual a 0, se asigna el valor original
        }
    }

    public double getCsPorMinuto() {
        return cs_por_minuto;
    }

    public void setCsPorMinuto(double csPorMinuto) {
        if (csPorMinuto < 0) {
            this.cs_por_minuto = 0; // Si csPorMinuto es negativo, asignamos 0
        } else {
            this.cs_por_minuto = csPorMinuto; // Si csPorMinuto es mayor o igual a 0, asignamos el valor original
        }
    }

    public int getParticipacionKill() {
        return participacion_kill;
    }

    public void setParticipacionKill(int participacionKill) {
        if (participacionKill < 0) {
            this.participacion_kill = 0; // Si el valor es negativo, asignamos 0
        } else {
            this.participacion_kill = participacionKill; // Si es positivo, asignamos el valor original
        }
        // Actualiza el campo formateado automáticamente
        this.participacionKillFormatted = this.participacion_kill + "%";
    }


    public String getParticipacionKillFormatted() {
        return participacionKillFormatted;
    }

    public void setParticipacionKillFormatted(String participacionKillFormatted) {
        try {
            // Elimina el signo de porcentaje, convierte a entero y valida que no sea negativo
            int value = Math.max(0, Integer.parseInt(participacionKillFormatted.replace("%", "").trim()));

            // Asigna el valor numérico y formateado
            this.participacion_kill = value;
            this.participacionKillFormatted = value + "%";
        } catch (NumberFormatException e) {
            // Si hay error, asigna valor por defecto
            this.participacion_kill = 0;
            this.participacionKillFormatted = "0%";
        }
    }

}

