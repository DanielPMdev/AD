package com.dpm.servicios;

import com.dpm.modelo.Equipo;
import com.dpm.modelo.Jugador;

import java.io.File;
import java.util.List;

/**
 * @author danielpm.dev
 */
public interface EquipoServicios {

    public boolean añadirEquipo(Equipo j);

    public boolean borrarEquipo(Equipo j);

    public boolean actualizarEquipo(Equipo j);

    public List<Equipo> filtrarEquipos(String criterio, String valor);

    public boolean exportarEquiposJSON(List<Equipo> listaEquipos, File archivo);

    public boolean exportarEquiposCSV(List<Equipo> listaEquipos, File archivo);

    public boolean exportarEquiposSQL(List<Equipo> listaEquipos, File archivo);


    public boolean exportarEquiposTXT(List<Equipo> listaEquipos, File archivo);

    public void guardarEquipos(List<Equipo> listaEquipos, File archivo);

    public void guardarEquiposCSV(List<Equipo> listaEquipos, File archivo);

    public List<Equipo> cargarEquipos(File archivo);

    public List<Equipo> cargarEquiposCSV(File archivo);

    public void guardarEquiposTXT (List<Equipo> listaEquipos, File archivo);

    //public List<Equipo> buscarAlumnoApellidos(String apellidos);
}
