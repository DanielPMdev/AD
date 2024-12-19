package com.dpm.repositorio;


import com.dpm.modelo.Jugador;

import java.io.File;
import java.util.List;

/**
 * @author danielpm.dev
 */
public interface JugadorDAO {

    public List<Jugador> consultarPorAtributo(String atributo, String valor);

    public boolean insertarJugador(Jugador j);

    public boolean actualizarJugador(Jugador j);

    public boolean eliminarJugador(Jugador j);

    public boolean exportarDatosAJSON(List<Jugador> listaJugadores, File archivo);

    public boolean exportarDatosACSV(List<Jugador> listaJugadores, File archivo);

    public boolean exportarDatosASQL(List<Jugador> listaJugadores, File archivo);

    public List<Jugador> mostrarJugadores ();

    public List<Jugador> importarDatosDesdeJSON(File archivo);

    //public List<Jugador> consultarPorApellidos(String nombreJugador);
}
