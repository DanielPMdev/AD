package com.dpm.repositorio;

import javax.swing.table.DefaultTableModel;

/**
 * @author danielpm.dev
 */
public interface ConsultasDAO {

    public DefaultTableModel obtenerJugadoresConEquipo();

    public DefaultTableModel obtenerJugadoresConEquipoYTitulos();

    public DefaultTableModel obtenerEquiposConTitulosLigaNacional();

    public DefaultTableModel obtenerJugadoresConBuenasEstadisticas();

    public DefaultTableModel obtenerEquiposConTitulosNacionalesOrdenados();

    public DefaultTableModel obtenerEquiposConTotalTitulos();
}
