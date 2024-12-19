package com.dpm.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author danielpm.dev
 */
public class VentanaPrincipal {
    private JPanel panelPrincipal;
    private JTable tablaEquipos;
    private JTable tablaJugadores;
    private JButton btInsertar;
    private JButton btEliminar;
    private JButton btActualizar;
    private JButton btConsultar;
    private JComboBox cbConsultar;
    private JButton btMigrarData;
    private JButton exportarButton;
    private JComboBox cbExportar;
    private JButton btEjecutarScript;
    private JButton btTablasSQLite;
    private JTextField txNombreEquipo;
    private JComboBox cbRegion;
    private JTextField txAnoFundacion;
    private JComboBox cbTituloTipo;
    private JSpinner sMSICantidad;
    private JTextField txEntrenador;
    private JTextField txNombreJugador;
    private JTextField txNacionalidad;
    private JComboBox cbPosicion;
    private JTextField txKDA;
    private JTextField txCSxMin;
    private JTextField txPartiKills;
    private JRadioButton rbEquipo;
    private JRadioButton rbJugador;
    private JButton btMostrarListas;
    private JSpinner sWorldsCantidad;
    private JSpinner sLigaCantidad;
    private JTextField txJugadoresxEquipo;
    private JSpinner sJugadorID;
    private JSpinner sEquipoID;
    private JSpinner sEquipoxJugador;
    private JButton btLimpiar;
    private JButton btConsultasAvanzadas;

    private DefaultTableModel dtmEquipo;
    private DefaultTableModel dtmJugador;

    public VentanaPrincipal() {
        JFrame frame = new JFrame("EsportsAnalytics");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //AÑADIR DTM Y SUS COLUMNAS SI VEO NECESARIO
        dtmEquipo = new DefaultTableModel();
        dtmJugador = new DefaultTableModel();

        String[] columnasEquipo = {"ID", "Nombre", "Región", "Año de Fundación", "Títulos", "Entrenador", "Jugadores"};
        String[] columnasJugador = {"ID", "Nombre", "Posición", "Nacionalidad", "KDA", "CSxMin", "Participación en Kills", "Id del Equipo"};

        dtmEquipo.setColumnIdentifiers(columnasEquipo);
        dtmJugador.setColumnIdentifiers(columnasJugador);

        tablaEquipos.setModel(dtmEquipo);
        tablaJugadores.setModel(dtmJugador);

        frame.pack();
        frame.setVisible(true);
    }


    //GET
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTable getTablaEquipos() {
        return tablaEquipos;
    }

    public JTable getTablaJugadores() {
        return tablaJugadores;
    }

    public JButton getBtInsertar() {
        return btInsertar;
    }

    public JButton getBtEliminar() {
        return btEliminar;
    }

    public JButton getBtActualizar() {
        return btActualizar;
    }

    public JButton getBtConsultar() {
        return btConsultar;
    }

    public JComboBox getCbConsultar() {
        return cbConsultar;
    }

    public JButton getBtMigrarData() {
        return btMigrarData;
    }

    public JButton getExportarButton() {
        return exportarButton;
    }

    public JComboBox getCbExportar() {
        return cbExportar;
    }

    public JButton getBtEjecutarScript() {
        return btEjecutarScript;
    }

    public JButton getBtTablasSQLite() {
        return btTablasSQLite;
    }

    public JTextField getTxNombreEquipo() {
        return txNombreEquipo;
    }

    public JComboBox getCbRegion() {
        return cbRegion;
    }

    public JTextField getTxAnoFundacion() {
        return txAnoFundacion;
    }

    public JComboBox getCbTituloTipo() {
        return cbTituloTipo;
    }

    public JSpinner getsTituloCantidad() {
        return sMSICantidad;
    }

    public JTextField getTxEntrenador() {
        return txEntrenador;
    }

    public JTextField getTxNombreJugador() {
        return txNombreJugador;
    }

    public JTextField getTxNacionalidad() {
        return txNacionalidad;
    }

    public JComboBox getCbPosicion() {
        return cbPosicion;
    }

    public JTextField getTxKDA() {
        return txKDA;
    }

    public JTextField getTxCSxMin() {
        return txCSxMin;
    }

    public JTextField getTxPartiKills() {
        return txPartiKills;
    }

    public JRadioButton getRbEquipo() {
        return rbEquipo;
    }

    public JRadioButton getRbJugador() {
        return rbJugador;
    }

    public JButton getBtMostrarListas() {
        return btMostrarListas;
    }

    public JSpinner getsMSICantidad() {
        return sMSICantidad;
    }

    public JSpinner getsWorldsCantidad() {
        return sWorldsCantidad;
    }

    public JSpinner getsLigaCantidad() {
        return sLigaCantidad;
    }

    public JTextField getTxJugadoresxEquipo() {
        return txJugadoresxEquipo;
    }

    public JSpinner getsJugadorID() {
        return sJugadorID;
    }

    public JSpinner getsEquipoID() {
        return sEquipoID;
    }

    public JSpinner getsEquipoxJugador() {
        return sEquipoxJugador;
    }

    public JButton getBtLimpiar() {
        return btLimpiar;
    }

    public JButton getBtConsultasAvanzadas() {
        return btConsultasAvanzadas;
    }

    public DefaultTableModel getDtmEquipo() {
        return dtmEquipo;
    }

    public DefaultTableModel getDtmJugador() {
        return dtmJugador;
    }

    //SET
    public void setCbPosicion(String m) {
        this.cbPosicion.setSelectedItem(m);
    }

    public void setCbRegion(String m) {
        this.cbRegion.setSelectedItem(m);
    }
}
