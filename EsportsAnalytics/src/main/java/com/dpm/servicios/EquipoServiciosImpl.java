package com.dpm.servicios;

import com.dpm.modelo.Equipo;
import com.dpm.modelo.Jugador;
import com.dpm.repositorio.EquipoDAO;
import com.dpm.repositorio.EquipoDAOImplNoSQL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danielpm.dev
 */
public class EquipoServiciosImpl implements EquipoServicios {

    private List<Equipo> listaEquipos;
    private EquipoDAO equipoDAO;

    public EquipoServiciosImpl() {
        this.equipoDAO = new EquipoDAOImplNoSQL();
        this.listaEquipos = new ArrayList<>();
    }

    public EquipoServiciosImpl (List<Equipo> lista) {
        this.listaEquipos = lista;
        this.equipoDAO = new EquipoDAOImplNoSQL();
    }

    @Override
    public boolean añadirEquipo(Equipo j) {
        return equipoDAO.insertarEquipo(j);
    }

    @Override
    public boolean borrarEquipo(Equipo j) {
        return equipoDAO.eliminarEquipo(j);
    }

    @Override
    public boolean actualizarEquipo(Equipo j) {
        return equipoDAO.actualizarEquipo(j);
    }

    @Override
    public List<Equipo> filtrarEquipos(String criterio, String valor) {
        return equipoDAO.consultarPorAtributo(criterio, valor);
    }

    @Override
    public boolean exportarEquiposJSON(List<Equipo> listaEquipos, File archivo) {
        return equipoDAO.exportarDatosAJSON(listaEquipos, archivo);
    }

    @Override
    public boolean exportarEquiposCSV(List<Equipo> listaEquipos, File archivo) {
        return equipoDAO.exportarDatosACSV(listaEquipos, archivo);
    }

    @Override
    public boolean exportarEquiposSQL(List<Equipo> listaEquipos, File archivo) {
        return equipoDAO.exportarDatosASQL(listaEquipos, archivo);
    }

    //SIN IMPLEMENTAR
    @Override
    public boolean exportarEquiposTXT(List<Equipo> listaEquipos, File archivo) {
        return false;
    }

    @Override
    public void guardarEquipos(List<Equipo> listaEquipos, File archivo) {

    }

    @Override
    public void guardarEquiposCSV(List<Equipo> listaEquipos, File archivo) {

    }

    @Override
    public List<Equipo> cargarEquipos(File archivo) {
        return List.of();
    }

    @Override
    public List<Equipo> cargarEquiposCSV(File archivo) {
        return List.of();
    }

    @Override
    public void guardarEquiposTXT(List<Equipo> listaEquipos, File archivo) {

    }

    public List<Equipo> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<Equipo> listaEquipos) { this.listaEquipos = listaEquipos;}
}
