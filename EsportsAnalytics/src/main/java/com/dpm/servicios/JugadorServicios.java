package com.dpm.servicios;

import com.dpm.modelo.Jugador;

import java.io.File;
import java.util.List;

/**
 * @author danielpm.dev
 */
public interface JugadorServicios {

    public boolean añadirJugador(Jugador j);

    public boolean borrarJugador(Jugador j);

    public boolean actualizarJugador(Jugador j);

    public List<Jugador> filtrarJugadores(String criterio, String valor);

    public boolean exportarJugadoresJSON(List<Jugador> listaJugadores, File archivo);

    public boolean exportarJugadoresCSV(List<Jugador> listaJugadores, File archivo);

    public boolean exportarJugadoresSQL(List<Jugador> listaJugadores, File archivo);


    public boolean exportarJugadoresTXT(List<Jugador> listaJugadores, File archivo);

    public void guardarJugadores(List<Jugador> listaJugadores, File archivo);

    public void guardarJugadoresCSV(List<Jugador> listaJugadores, File archivo);

    public List<Jugador> cargarJugadores(File archivo);

    public List<Jugador> cargarJugadoresCSV(File archivo);

    public void guardarJugadoresTXT (List<Jugador> listaJugadores, File archivo);

    //public List<Jugador> buscarAlumnoApellidos(String apellidos);
}
