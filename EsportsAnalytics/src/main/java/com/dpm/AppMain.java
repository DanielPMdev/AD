package com.dpm;

import com.dpm.controlador.Controlador;
import com.dpm.modelo.ModeloEquipos;
import com.dpm.modelo.ModeloJugadores;
import com.dpm.vistas.VentanaPrincipal;

/**
 * @author danielpm.dev
 */
public class AppMain {

    public static void main(String[] args) {
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        //AÑADIR EL SERVICIO CORRESPONDIENTE
        ModeloEquipos modeloEquipos = new ModeloEquipos();
        ModeloJugadores modeloJugadores = new ModeloJugadores();

        Controlador controlador = new Controlador(ventanaPrincipal, modeloEquipos, modeloJugadores);

    }
}
