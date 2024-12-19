package com.dpm.servicios;

import com.dpm.modelo.Jugador;
import com.dpm.repositorio.JugadorDAO;
import com.dpm.repositorio.JugadorDAOImplNoSQL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danielpm.dev
 */
public class JugadorServiciosImpl implements JugadorServicios {

    private List<Jugador> listaJugadores;
    private JugadorDAO jugadorDAO;

    public JugadorServiciosImpl() {
        this.listaJugadores = new ArrayList<>();
        this.jugadorDAO = new JugadorDAOImplNoSQL();
    }

    public JugadorServiciosImpl (List<Jugador> lista) {
        this.listaJugadores = lista;
        this.jugadorDAO = new JugadorDAOImplNoSQL();
    }

    @Override
    public boolean añadirJugador(Jugador j) {
        return jugadorDAO.insertarJugador(j);
    }

    @Override
    public boolean borrarJugador(Jugador j) {
        return jugadorDAO.eliminarJugador(j);
    }

    @Override
    public boolean actualizarJugador(Jugador j) {
        return jugadorDAO.actualizarJugador(j);
    }

    @Override
    public List<Jugador> filtrarJugadores(String criterio, String valor) {
        return jugadorDAO.consultarPorAtributo(criterio, valor);
    }

    @Override
    public boolean exportarJugadoresJSON(List<Jugador> listaJugadores, File archivo) {
        return jugadorDAO.exportarDatosAJSON(listaJugadores, archivo);
    }

    @Override
    public boolean exportarJugadoresCSV(List<Jugador> listaJugadores, File archivo) {
        return jugadorDAO.exportarDatosACSV(listaJugadores, archivo);
    }

    @Override
    public boolean exportarJugadoresSQL(List<Jugador> listaJugadores, File archivo) {
        return jugadorDAO.exportarDatosASQL(listaJugadores, archivo);
    }

    //SIN IMPLEMENTAR

    @Override
    public boolean exportarJugadoresTXT(List<Jugador> listaJugadores, File archivo) {
        return false;
    }

    @Override
    public void guardarJugadores(List<Jugador> listaJugadores, File archivo) {

    }

    @Override
    public void guardarJugadoresCSV(List<Jugador> listaJugadores, File archivo) {

    }

    @Override
    public List<Jugador> cargarJugadores(File archivo) {
        return List.of();
    }

    @Override
    public List<Jugador> cargarJugadoresCSV(File archivo) {
        return List.of();
    }

    @Override
    public void guardarJugadoresTXT(List<Jugador> listaJugadores, File archivo) {

    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Jugador> listaJugadores) { this.listaJugadores = listaJugadores;}

}
