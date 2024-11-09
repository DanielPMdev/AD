package org.dmp.vistas;

import org.dmp.modelo.Hobby;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Ventana{
    private JTable tableContenido;
    private JTextField tfNombre;
    private javax.swing.JPanel JPanel;
    private JTextField tfLocalidad;
    private JTextField tfApellidos;
    private JTextField tfCorreo;
    private JTextField tfTelefono;
    private JTextField tfFechaNacimiento;
    private JButton añadirButton;
    private JButton borrarButton;
    private JButton consultarButton;
    private JButton actualizarAlumnoButton;
    private JButton guardarAlumnosButton;
    private JButton cargarAlumnosButton;
    private JButton cargarDesdeCSVButton;
    private JButton guardarEnCSVButton;
    private JComboBox cbFiltro;
    private JCheckBox cbColeccionar;
    private JCheckBox deporteCheckBox;
    private JCheckBox jardineriaCheckBox;
    private JCheckBox videojuegosCheckBox;
    private JCheckBox lecturaCheckBox;
    private JCheckBox musicaCheckBox;
    private JCheckBox senderismoCheckBox;
    private JCheckBox voluntariadoCheckBox;
    private JCheckBox pinturaEsculturaCheckBox;
    private JCheckBox fotografiaCheckBox;
    private JCheckBox otrosCheckBox;
    private JSlider nivelOrdenador;
    private JRadioButton rbCarnet;
    private JRadioButton rbNoCarnet;
    private JComboBox cbEstudios;
    private JComboBox cbMotivacion;
    private JComboBox cbCiclo;
    private JLabel lbNombre;
    private JLabel lbApellidos;
    private JLabel lbTelefono;
    private JLabel lbCorreo;
    private JLabel lbLocalidad;
    private JLabel lbEstudios;
    private JLabel lbFechaNacimiento;
    private JLabel lbCiclo;
    private JLabel lbNivelOrdenador;
    private JLabel lbCarnet;
    private JLabel lbMotivacion;
    private JButton limpiarButton;
    private JButton guardarTXTButton;
    private JButton buscarApellidosButton;
    private JList listArchivos;
    private JTextArea txaContenidoArchivos;
    private JButton cargarFicherosButton;
    private JButton cargarDesdeJSONButton;
    private JButton guardarEnJSONButton;
    private JButton cargarDesdeXMLButton;
    private JButton guardarEnXMLButton;
    private JButton limpiarTextAreaButton;
    private JButton guardarEnYamlButton;
    private JButton cargarDesdeYamlButton;
    private JLabel lbHobbies;
    private JScrollPane panelPrincipal;
    private JButton consolaButton;
    private JButton mostrarListaButton;
    private DefaultTableModel dtm;
    private DefaultListModel dlm;

    public Ventana() {
        JFrame frame = new JFrame("Gestor de Alumnos");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dtm = new DefaultTableModel();
        tableContenido.setModel(dtm);
        dlm = new DefaultListModel();
        listArchivos.setModel(dlm);
        dtm.addColumn("Nombre");
        dtm.addColumn("Apellidos");
        dtm.addColumn("e-mail");
        dtm.addColumn("Teléfono");
        dtm.addColumn("Localidad");
        dtm.addColumn("Estudios");
        dtm.addColumn("Fecha Nacimiento");
        dtm.addColumn("Ciclo");
        dtm.addColumn("Nivel Ordenador");
        dtm.addColumn("Carnet de conducir");
        dtm.addColumn("Motivación");
        dtm.addColumn("Hobbies");
        txaContenidoArchivos.setLineWrap(true);   // Activa el ajuste de línea
        txaContenidoArchivos.setWrapStyleWord(true); // Ajusta el texto por palabras
        frame.pack();
        frame.setVisible(true);
    }

    public JTable getTableContenido() {
        return tableContenido;
    }

    public void setTableContenido(JTable tableContenido) {
        this.tableContenido = tableContenido;
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(String m) {
        this.tfNombre.setText(m);
    }

    public JTextField getTfLocalidad() {
        return tfLocalidad;
    }

    public void setTfLocalidad(String m) {
        this.tfLocalidad.setText(m);
    }

    public JTextField getTfApellidos() {
        return tfApellidos;
    }

    public void setTfApellidos(String m) {
        this.tfApellidos.setText(m);
    }

    public JTextField getTfCorreo() {
        return tfCorreo;
    }

    public void setTfCorreo(String m) {
        this.tfCorreo.setText(m);
    }

    public JTextField getTfTelefono() {
        return tfTelefono;
    }

    public void setTfTelefono(String m) {
        this.tfTelefono.setText(m);
    }

    public JTextField getTfFechaNacimiento() {
        return tfFechaNacimiento;
    }

    public void setTfFechaNacimiento(String m) {
        this.tfFechaNacimiento.setText(m);
    }

    public JButton getAñadirButton() {
        return añadirButton;
    }

    public void setAñadirButton(JButton añadirButton) {
        this.añadirButton = añadirButton;
    }

    public JButton getBorrarButton() {
        return borrarButton;
    }

    public void setBorrarButton(JButton borrarButton) {
        this.borrarButton = borrarButton;
    }

    public JButton getConsultarButton() {
        return consultarButton;
    }

    public void setConsultarButton(JButton consultarButton) {
        this.consultarButton = consultarButton;
    }

    public JButton getActualizarAlumnoButton() {
        return actualizarAlumnoButton;
    }

    public void setActualizarAlumnoButton(JButton actualizarAlumnoButton) {
        this.actualizarAlumnoButton = actualizarAlumnoButton;
    }

    public JButton getGuardarAlumnosButton() {
        return guardarAlumnosButton;
    }

    public void setGuardarAlumnosButton(JButton guardarAlumnosButton) {
        this.guardarAlumnosButton = guardarAlumnosButton;
    }

    public JButton getCargarAlumnosButton() {
        return cargarAlumnosButton;
    }

    public void setCargarAlumnosButton(JButton cargarAlumnosButton) {
        this.cargarAlumnosButton = cargarAlumnosButton;
    }

    public JButton getCargarDesdeCSVButton() {
        return cargarDesdeCSVButton;
    }

    public void setCargarDesdeCSVButton(JButton cargarDesdeCSVButton) {
        this.cargarDesdeCSVButton = cargarDesdeCSVButton;
    }

    public JButton getGuardarEnCSVButton() {
        return guardarEnCSVButton;
    }

    public void setGuardarEnCSVButton(JButton guardarEnCSVButton) {
        this.guardarEnCSVButton = guardarEnCSVButton;
    }

    public JComboBox getCbFiltro() {
        return cbFiltro;
    }

    public void setCbFiltro(JComboBox cbFiltro) {
        this.cbFiltro = cbFiltro;
    }

    public JCheckBox getCbColeccionar() {
        return cbColeccionar;
    }

    public void setListaHobbies(ArrayList<Hobby> hobbies) {
        // Primero, deseleccionamos todos los CheckBox
        cbColeccionar.setSelected(false);
        deporteCheckBox.setSelected(false);
        jardineriaCheckBox.setSelected(false);
        videojuegosCheckBox.setSelected(false);
        lecturaCheckBox.setSelected(false);
        musicaCheckBox.setSelected(false);
        senderismoCheckBox.setSelected(false);
        voluntariadoCheckBox.setSelected(false);
        pinturaEsculturaCheckBox.setSelected(false);
        fotografiaCheckBox.setSelected(false);
        otrosCheckBox.setSelected(false);

        // Recorremos la lista de hobbies y seleccionamos los CheckBox correspondientes
        for (Hobby hobby : hobbies) {
            switch (hobby) {
                case COLECCIONAR:
                    cbColeccionar.setSelected(true);
                    break;
                case DEPORTE:
                    deporteCheckBox.setSelected(true);
                    break;
                case JARDINERIA:
                    jardineriaCheckBox.setSelected(true);
                    break;
                case VIDEOJUEGOS:
                    videojuegosCheckBox.setSelected(true);
                    break;
                case LECTURA:
                    lecturaCheckBox.setSelected(true);
                    break;
                case MUSICA:
                    musicaCheckBox.setSelected(true);
                    break;
                case SENDERISMO:
                    senderismoCheckBox.setSelected(true);
                    break;
                case VOLUNTARIADO:
                    voluntariadoCheckBox.setSelected(true);
                    break;
                case PINTURA_ESCULTURA:
                    pinturaEsculturaCheckBox.setSelected(true);
                    break;
                case FOTOGRAFIA:
                    fotografiaCheckBox.setSelected(true);
                    break;
                case OTROS:
                    otrosCheckBox.setSelected(true);
                    break;
            }
        }
    }


    public void setCbColeccionar(JCheckBox cbColeccionar) {
        this.cbColeccionar = cbColeccionar;
    }

    public JCheckBox getDeporteCheckBox() {
        return deporteCheckBox;
    }

    public void setDeporteCheckBox(JCheckBox deporteCheckBox) {
        this.deporteCheckBox = deporteCheckBox;
    }

    public JCheckBox getJardineriaCheckBox() {
        return jardineriaCheckBox;
    }

    public void setJardineriaCheckBox(JCheckBox jardineriaCheckBox) {
        this.jardineriaCheckBox = jardineriaCheckBox;
    }

    public JCheckBox getVideojuegosCheckBox() {
        return videojuegosCheckBox;
    }

    public void setVideojuegosCheckBox(JCheckBox videojuegosCheckBox) {
        this.videojuegosCheckBox = videojuegosCheckBox;
    }

    public JCheckBox getLecturaCheckBox() {
        return lecturaCheckBox;
    }

    public void setLecturaCheckBox(JCheckBox lecturaCheckBox) {
        this.lecturaCheckBox = lecturaCheckBox;
    }

    public JCheckBox getMusicaCheckBox() {
        return musicaCheckBox;
    }

    public void setMusicaCheckBox(JCheckBox musicaCheckBox) {
        this.musicaCheckBox = musicaCheckBox;
    }

    public JCheckBox getSenderismoCheckBox() {
        return senderismoCheckBox;
    }

    public void setSenderismoCheckBox(JCheckBox senderismoCheckBox) {
        this.senderismoCheckBox = senderismoCheckBox;
    }

    public JCheckBox getVoluntariadoCheckBox() {
        return voluntariadoCheckBox;
    }

    public void setVoluntariadoCheckBox(JCheckBox voluntariadoCheckBox) {
        this.voluntariadoCheckBox = voluntariadoCheckBox;
    }

    public JCheckBox getPinturaEsculturaCheckBox() {
        return pinturaEsculturaCheckBox;
    }

    public void setPinturaEsculturaCheckBox(JCheckBox pinturaEsculturaCheckBox) {
        this.pinturaEsculturaCheckBox = pinturaEsculturaCheckBox;
    }

    public JCheckBox getFotografiaCheckBox() {
        return fotografiaCheckBox;
    }

    public void setFotografiaCheckBox(JCheckBox fotografiaCheckBox) {
        this.fotografiaCheckBox = fotografiaCheckBox;
    }

    public JCheckBox getOtrosCheckBox() {
        return otrosCheckBox;
    }

    public void setOtrosCheckBox(JCheckBox otrosCheckBox) {
        this.otrosCheckBox = otrosCheckBox;
    }

    public int getNivelOrdenador() {
        return nivelOrdenador.getValue();
    }

    public void setNivelOrdenador(int n) {
        this.nivelOrdenador.setValue(n);
    }

    public String getRbCarnet() {
        if (rbCarnet.isSelected()) {
            return "Tengo Carnet";
        } else {
            return "No tengo Carnet";
        }
    }

    public void setRbCarnet(String carnet) {
        if (carnet.equals("Tengo Carnet")) {
            rbCarnet.setSelected(true);
            rbNoCarnet.setSelected(false);
        } else {
            rbCarnet.setSelected(false);
            rbNoCarnet.setSelected(true);
        }
    }

    public JRadioButton getRbNoCarnet() {
        return rbNoCarnet;
    }

    public void setRbNoCarnet(JRadioButton rbNoCarnet) {
        this.rbNoCarnet = rbNoCarnet;
    }

    public JComboBox getCbEstudios() {
        return cbEstudios;
    }

    public void setCbEstudios(String m) {
        this.cbEstudios.setSelectedItem(m);
    }

    public JComboBox getCbMotivacion() {
        return cbMotivacion;
    }

    public void setCbMotivacion(String m) {
        this.cbMotivacion.setSelectedItem(m);
    }

    public JComboBox getCbCiclo() {
        return cbCiclo;
    }

    public void setCbCiclo(String m) {
        this.cbCiclo.setSelectedItem(m);
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }

    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }

    public javax.swing.JPanel getJPanel() {
        return JPanel;
    }

    public void setJPanel(javax.swing.JPanel JPanel) {
        this.JPanel = JPanel;
    }

    public JButton getLimpiarButton() {
        return limpiarButton;
    }

    public JButton getGuardarTXTButton() {
        return guardarTXTButton;
    }

    public JButton getBuscarApellidosButton() {
        return buscarApellidosButton;
    }

    public JList getListArchivos() {
        return listArchivos;
    }

    public void setListArchivos(JList listArchivos) {
        this.listArchivos = listArchivos;
    }

    public JButton getCargarFicherosButton() {
        return cargarFicherosButton;
    }

    public void setCargarFicherosButton(JButton cargarFicherosButton) {
        this.cargarFicherosButton = cargarFicherosButton;
    }

    public DefaultListModel getDlm() {
        return dlm;
    }

    public JTextArea getTxaContenidoArchivos() {
        return txaContenidoArchivos;
    }

    public void setTxaContenidoArchivos(JTextArea txaContenidoArchivos) {
        this.txaContenidoArchivos = txaContenidoArchivos;
    }

    public JButton getGuardarEnXMLButton() {
        return guardarEnXMLButton;
    }

    public JButton getCargarDesdeXMLButton() {
        return cargarDesdeXMLButton;
    }

    public JButton getGuardarEnJSONButton() {
        return guardarEnJSONButton;
    }

    public JButton getCargarDesdeJSONButton() {
        return cargarDesdeJSONButton;
    }

    public JButton getLimpiarTextAreaButton() {
        return limpiarTextAreaButton;
    }

    public JButton getMostrarListaButton() {
        return mostrarListaButton;
    }
}
