package com.dpm.modelo;

/**
 * @author danielpm.dev
 */
import com.dpm.servicios.JugadorServicios;
import com.dpm.servicios.JugadorServiciosImpl;

import java.util.ArrayList;
import java.util.List;

public class ModeloJugadores {

    private List<Jugador> listaJugadores;
    private JugadorServicios serviciosJugador;

    /**
     * Constructor de la clase {@link ModeloJugadores}.
     *
     * Este constructor inicializa la lista de jugadores y asigna el servicio adecuado
     * para gestionar dicha lista.
     */
    public ModeloJugadores(JugadorServicios serviciosJugador) {
        this.listaJugadores = new ArrayList<>();

        // Asegúrate de que el servicio tenga la lista de jugadores si es necesario
        if (serviciosJugador instanceof JugadorServiciosImpl) {
            ((JugadorServiciosImpl) serviciosJugador).setListaJugadores(listaJugadores);
        }
//        } else if (serviciosJugador instanceof ServiciosImplSQL) {
//            ((ServiciosImplSQL) servicios).setListaJugadores(listaJugadores);
//        }

        this.serviciosJugador = serviciosJugador;
    }

    /**
     * Constructor de la clase {@link ModeloJugadores}.
     *
     * Este constructor inicializa la lista de jugadores como una nueva lista vacía
     * y crea una instancia de {@link JugadorServiciosImpl}, proporcionando los serviciosJugador
     * necesarios para gestionar la lista de jugadores.
     */
    public ModeloJugadores() {
        listaJugadores = new ArrayList<Jugador>();
        this.serviciosJugador = new JugadorServiciosImpl(listaJugadores);
    }

    // Métodos getter y setter
    public JugadorServicios getServiciosJugador() {
        return serviciosJugador;
    }

    public void setServiciosJugador(JugadorServicios serviciosJugador) {
        this.serviciosJugador = serviciosJugador;
    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;

        // Realizar un cast a ServiciosImpl para llamar al methods
        if (this.serviciosJugador instanceof JugadorServiciosImpl) {
            ((JugadorServiciosImpl) this.serviciosJugador).setListaJugadores(this.listaJugadores);
        }
    }
}

