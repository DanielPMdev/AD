package com.dpm.modelo;

import com.dpm.servicios.EquipoServicios;
import com.dpm.servicios.EquipoServiciosImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danielpm.dev
 */
public class ModeloEquipos {

    private List<Equipo> listaEquipos;
    private EquipoServicios serviciosEquipo;

    public ModeloEquipos(EquipoServicios serviciosEquipo) {
        this.listaEquipos = new ArrayList<>();

        // Asegúrate de que el servicio tenga la lista de jugadores si es necesario
        if (serviciosEquipo instanceof EquipoServiciosImpl) {
            ((EquipoServiciosImpl) serviciosEquipo).setListaEquipos(listaEquipos);
        }
//        } else if (serviciosJugador instanceof ServiciosImplSQL) {
//            ((ServiciosImplSQL) servicios).setListaJugadores(listaJugadores);
//        }

        this.serviciosEquipo = serviciosEquipo;
    }

    public ModeloEquipos() {
        listaEquipos = new ArrayList<Equipo>();
        this.serviciosEquipo = new EquipoServiciosImpl(listaEquipos);
    }

    // Métodos getter y setter
    public EquipoServicios getServiciosEquipo() {
        return serviciosEquipo;
    }

    public void setServiciosEquipo(EquipoServicios serviciosEquipo) {
        this.serviciosEquipo = serviciosEquipo;
    }

    public List<Equipo> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;

        // Realizar un cast a ServiciosImpl para llamar al methods
        if (this.serviciosEquipo instanceof EquipoServiciosImpl) {
            ((EquipoServiciosImpl) this.serviciosEquipo).setListaEquipos(this.listaEquipos);
        }
    }
}
