package com.dpm.vistas;

import javax.swing.*;

public class ConsultasAvanzadas extends JDialog {
    private JPanel contentPane;
    private JTable tablaConsultas;
    private JButton btEjecutarConAvanzada;
    private JComboBox cbTiposConsultas;
    private JTextArea txaInfo;

    public ConsultasAvanzadas() {
        setContentPane(contentPane);
        setModal(true);
    }

    public JTable getTablaConsultas() {
        return tablaConsultas;
    }

    public JButton getBtEjecutarConAvanzada() {
        return btEjecutarConAvanzada;
    }

    public JComboBox getCbTiposConsultas() {
        return cbTiposConsultas;
    }

    public JTextArea getTxaInfo() {
        return txaInfo;
    }

    public static void main(String[] args) {
        ConsultasAvanzadas dialog = new ConsultasAvanzadas();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
