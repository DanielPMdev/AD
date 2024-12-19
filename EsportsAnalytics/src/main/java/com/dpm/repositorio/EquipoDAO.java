package com.dpm.repositorio;

import com.dpm.modelo.Equipo;

import java.io.File;
import java.util.List;

/**
 * @author danielpm.dev
 */
public interface EquipoDAO {

    
    public List<Equipo> consultarPorAtributo(String atributo, String valor);

    public boolean insertarEquipo(Equipo e);

    public boolean actualizarEquipo(Equipo e);

    public boolean eliminarEquipo(Equipo e);

    public List<Equipo> importarDatosDesdeJSON(File archivo);

    public boolean exportarDatosAJSON(List<Equipo> listaEquipos, File archivo);

    public boolean exportarDatosACSV(List<Equipo> listaEquipos, File archivo);

    public boolean exportarDatosASQL(List<Equipo> listaEquipos, File archivo);
    
    public List<Equipo> mostrarEquipos ();

    //public List<Equipo> consultarPorApellidos(String nombreEquipo);
}
