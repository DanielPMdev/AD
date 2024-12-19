package com.dpm.controlador;

import com.dpm.modelo.*;
import com.dpm.repositorio.*;
import com.dpm.servicios.EquipoServiciosImpl;
import com.dpm.util.DataMigration;
import com.dpm.vistas.ConsultasAvanzadas;
import com.dpm.vistas.VentanaPrincipal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author danielpm.dev
 */
public class Controlador {

    private VentanaPrincipal ventanaPrincipal;
    private ModeloEquipos modeloEquipos;
    private ModeloJugadores modeloJugador;

    private EquipoDAO equipoDAO;
    private JugadorDAO jugadorDAO;


    public Controlador(VentanaPrincipal ventanaPrincipal, ModeloEquipos modeloEquipos, ModeloJugadores modeloJugador) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.modeloEquipos = modeloEquipos;
        this.modeloJugador = modeloJugador;

        this.equipoDAO = new EquipoDAOImplNoSQL();
        this.jugadorDAO = new JugadorDAOImplNoSQL();

        //LISTENERS
        this.ventanaPrincipal.getBtInsertar().addActionListener(e -> {
            if (ventanaPrincipal.getRbEquipo().isSelected()) {
                insertarEquipo();
            } else {
                insertarJugador();
            }
        });

        this.ventanaPrincipal.getBtEliminar().addActionListener(e -> {
            if (ventanaPrincipal.getRbEquipo().isSelected()) {
                eliminarEquipo();
            } else {
                eliminarJugador();
            }
        });

        this.ventanaPrincipal.getBtActualizar().addActionListener(e -> {
            if (ventanaPrincipal.getRbEquipo().isSelected()) {
                actualizarEquipo();
            } else {
                actualizarJugador();
            }
        });

        this.ventanaPrincipal.getBtLimpiar().addActionListener(e ->{
            if (ventanaPrincipal.getRbEquipo().isSelected()) {
                limpiarEquipo();
            } else {
                limpiarJugador();
            }
        });

        this.ventanaPrincipal.getBtConsultar().addActionListener(e -> consultar());

        this.ventanaPrincipal.getBtMostrarListas().addActionListener(e -> cargarLista());

        this.ventanaPrincipal.getExportarButton().addActionListener(e -> exportar());

        this.ventanaPrincipal.getBtMigrarData().addActionListener(e -> {
            try {
                migrarData();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        this.ventanaPrincipal.getBtEjecutarScript().addActionListener(e -> ejecutarScript());

        this.ventanaPrincipal.getBtConsultasAvanzadas().addActionListener(e -> abrirDialogoConsultasAvanzadas());

        //LISTENERS JTABLES
        this.ventanaPrincipal.getTablaEquipos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFilaTablaEquipos();
            }
        });

        this.ventanaPrincipal.getTablaJugadores().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFilaTablaJugadores();
            }
        });
    }


    //AÑADIR
    private void insertarEquipo() {
        Equipo equipo = obtenerEquipo();
        modeloEquipos.getServiciosEquipo().añadirEquipo(equipo);

        //Actualiza los JTABLES, REVISAR ESTO PUEDE QUE NO HAGA FALTA 1 de 2
        mostrarEquipos();
        cargarLista();
    }

    private void insertarJugador() {
        Jugador jugador = obtenerJugador();
        modeloJugador.getServiciosJugador().añadirJugador(jugador);
        //Actualiza los JTABLES
        mostrarJugadores();
        cargarLista();
    }

    //ELIMINAR
    private void eliminarEquipo() {
        Equipo equipo = obtenerEquipo();
        modeloEquipos.getServiciosEquipo().borrarEquipo(equipo);

        //Actualiza los JTABLES
        mostrarEquipos();
        cargarLista();
    }

    private void eliminarJugador() {
        Jugador jugador = obtenerJugador();
        modeloJugador.getServiciosJugador().borrarJugador(jugador);

        //Actualiza los JTABLES
        mostrarJugadores();
        cargarLista();
    }

    //ACTUALIZAR
    private void actualizarEquipo() {
        Equipo equipo = obtenerEquipo();
        modeloEquipos.getServiciosEquipo().actualizarEquipo(equipo);

        //Actualiza los JTABLES
        mostrarEquipos();
        cargarLista();
    }

    private void actualizarJugador() {
        Jugador jugador = obtenerJugador();
        modeloJugador.getServiciosJugador().actualizarJugador(jugador);

        //Actualiza los JTABLES
        mostrarJugadores();
        cargarLista();
    }

    //CONSULTAR
    public void consultar() {
        String criterio = (String) ventanaPrincipal.getCbConsultar().getSelectedItem();
        String valor;
        int tipoServicios = -1;

        switch (criterio) {
            case "Region" -> {
                valor = (String) ventanaPrincipal.getCbRegion().getSelectedItem();
                tipoServicios = 0;
            }
            case "Posicion" -> {
                valor = (String) ventanaPrincipal.getCbPosicion().getSelectedItem();
                tipoServicios = 1;
            }

            case "Nacionalidad" -> {
                valor = ventanaPrincipal.getTxNacionalidad().getText();
                tipoServicios = 1;
            }
            case null, default -> valor = "";
        }

        if (tipoServicios == 0){
            modeloEquipos.setListaEquipos(modeloEquipos.getServiciosEquipo().filtrarEquipos(criterio, valor));
        } else if (tipoServicios == 1){
            modeloJugador.setListaJugadores(modeloJugador.getServiciosJugador().filtrarJugadores(criterio, valor));
        }
        mostrarEquipos();
        mostrarJugadores();
    }

    //EXPORTAR
    public void exportar() {
        String extension = (String) ventanaPrincipal.getCbExportar().getSelectedItem();

        switch (extension){
            case "JSON" ->{
                guardarJSON(0);
                guardarJSON(1);
            }
            case "CSV" ->{
                guardarCSV(0);
                guardarCSV(1);
            }
            case "SQL" ->{
                guardarSQL(0);
                guardarSQL(1);
            }
            case null, default -> throw new IllegalStateException("Unexpected value: " + extension);
        }
    }

    public void guardarJSON(int i) {
        File archivo = mostrarFileChooser(JFileChooser.SAVE_DIALOG, "Guardar archivo", "json");

        if (archivo != null) {
            // Verificar si el archivo ya existe
            if (archivo.exists()) {
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "El archivo ya existe. ¿Quieres sobrescribirlo?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (respuesta != JOptionPane.YES_OPTION) {
                    return; // Cancelar la operación si el usuario elige no sobrescribir
                }
            }

            if (i == 0){
                modeloEquipos.getServiciosEquipo().exportarEquiposJSON(modeloEquipos.getListaEquipos(), archivo);
            } else if (i == 1){
                modeloJugador.getServiciosJugador().exportarJugadoresJSON(modeloJugador.getListaJugadores(), archivo);
            }
        }
    }

    public void guardarCSV(int i) {
        File archivo = mostrarFileChooser(JFileChooser.SAVE_DIALOG, "Guardar archivo", "csv");

        if (archivo != null) {
            // Verificar si el archivo ya existe
            if (archivo.exists()) {
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "El archivo ya existe. ¿Quieres sobrescribirlo?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (respuesta != JOptionPane.YES_OPTION) {
                    return; // Cancelar la operación si el usuario elige no sobrescribir
                }
            }

            if (i == 0){
                modeloEquipos.getServiciosEquipo().exportarEquiposCSV(modeloEquipos.getListaEquipos(), archivo);
            } else if (i == 1){
                modeloJugador.getServiciosJugador().exportarJugadoresCSV(modeloJugador.getListaJugadores(), archivo);
            }
        }
    }

    public void guardarSQL(int i){
        File archivo = mostrarFileChooser(JFileChooser.SAVE_DIALOG, "Guardar archivo", "sql");

        if (archivo != null) {
            // Verificar si el archivo ya existe
            if (archivo.exists()) {
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "El archivo ya existe. ¿Quieres sobrescribirlo?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (respuesta != JOptionPane.YES_OPTION) {
                    return; // Cancelar la operación si el usuario elige no sobrescribir
                }
            }

            if (i == 0){
                modeloEquipos.getServiciosEquipo().exportarEquiposSQL(modeloEquipos.getListaEquipos(), archivo);
            } else if (i == 1){
                modeloJugador.getServiciosJugador().exportarJugadoresSQL(modeloJugador.getListaJugadores(), archivo);
            }
        }
    }

    /**
     * Muestra un FileChooser para seleccionar un archivo para guardar o cargar.
     *
     * @param modo      Puede ser JFileChooser.SAVE_DIALOG o JFileChooser.OPEN_DIALOG.
     * @param titulo    El título del diálogo.
     * @param extension La extensión del archivo que se filtra (ej. "dat").
     * @return El archivo seleccionado por el usuario, o null si la operación fue cancelada.
     */
    public static File mostrarFileChooser(int modo, String titulo, String extension) {
        JFileChooser fileChooser = new JFileChooser();

        // Establecer la ruta por defecto
        fileChooser.setCurrentDirectory(new File("../EsportsAnalytics/archivos"));

        // Configurar el diálogo
        fileChooser.setDialogTitle(titulo);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos " + extension, extension));

        int userSelection;
        if (modo == JFileChooser.SAVE_DIALOG) {
            userSelection = fileChooser.showSaveDialog(null);
        } else {
            userSelection = fileChooser.showOpenDialog(null);
        }

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            // Si es un diálogo de guardado y falta la extensión, añadirla
            if (modo == JFileChooser.SAVE_DIALOG && !archivo.getAbsolutePath().endsWith("." + extension)) {
                archivo = new File(archivo + "." + extension);
            }

            return archivo; // Retorna el archivo seleccionado
        } else {
            System.out.println("El usuario canceló la operación.");
            return null; // Retorna null si se canceló la operación
        }
    }

    //MOSTRAR
    public void mostrarEquipos() {
        ventanaPrincipal.getDtmEquipo().setRowCount(0); // Limpiar las filas actuales de la tabla

        // Iterar sobre la lista de equipos
        for (Equipo e : modeloEquipos.getListaEquipos()) {
            Object[] fila = new Object[7]; // 7 Campos para los equipos (incluyendo id y listas)

            // Asignar los valores a las filas de la tabla
            fila[0] = e.getId(); // Id del equipo
            fila[1] = e.getNombre(); // Nombre
            fila[2] = e.getRegion(); // Región
            fila[3] = e.getAnoFundacion(); // Año de fundación
            fila[4] = formatTitulos(e.getTitulos()); // Títulos formateados
            fila[5] = e.getEntrenador(); // Entrenador
            fila[6] = formatJugadores(e.getJugadores()); // Jugadores formateados

            // Añadir la fila a la tabla
            ventanaPrincipal.getDtmEquipo().addRow(fila);
        }
    }

    private String formatTitulos(List<Titulo> titulos) {
        if (titulos == null || titulos.isEmpty()) {
            return "No títulos";
        }
        StringBuilder sb = new StringBuilder();
        for (Titulo t : titulos) {
            sb.append(t.getTipo())  // Tipo del título
                    .append(" (")
                    .append(t.getCantidad()) // Cantidad de títulos
                    .append("), ");
        }
        return sb.toString().isEmpty() ? "No títulos" : sb.substring(0, sb.length() - 2); // Eliminar la última coma
    }

    private String formatJugadores(List<Integer> jugadores) {
        if (jugadores == null || jugadores.isEmpty()) {
            return "No jugadores";
        }
        return jugadores.toString(); // Devuelve la lista de jugadores como cadena
    }


    public void mostrarJugadores() {
        ventanaPrincipal.getDtmJugador().setRowCount(0); // Limpiar las filas actuales de la tabla

        // Iterar sobre la lista de jugadores
        for (Jugador j : modeloJugador.getListaJugadores()) {
            Object[] fila = new Object[8];
            fila[0] = j.getId(); //ID
            fila[1] = j.getNombre(); // Nombre
            fila[2] = j.getPosicion(); // Posición
            fila[3] = j.getNacionalidad(); // Nacionalidad
            fila[4] = j.getEstadisticas().getKda(); // KDA
            fila[5] = j.getEstadisticas().getCsPorMinuto(); //CSxMin
            fila[6] = j.getEstadisticas().getParticipacionKillFormatted(); // Participación en kills (formateada)
            fila[7] = j.getIdEquipo(); // Id del equipo

            // Añadir la fila a la tabla
            ventanaPrincipal.getDtmJugador().addRow(fila);
        }
    }

    public void cargarLista() {
        if (modeloEquipos.getServiciosEquipo() instanceof EquipoServiciosImpl) {
            List<Equipo> listaCargadaEquipos = null;
            List<Jugador> listaCargadaJugadores = null;
            listaCargadaEquipos = equipoDAO.mostrarEquipos();
            listaCargadaJugadores = jugadorDAO.mostrarJugadores();
            if (listaCargadaEquipos != null && listaCargadaJugadores != null) {
                modeloEquipos.setListaEquipos(listaCargadaEquipos);
                modeloJugador.setListaJugadores(listaCargadaJugadores);
                mostrarEquipos();
                mostrarJugadores();
            }
        }
    }

    public void limpiarEquipo(){
        ventanaPrincipal.getsEquipoID().setValue(0);
        ventanaPrincipal.getTxNombreEquipo().setText("");
        ventanaPrincipal.setCbRegion((String) ventanaPrincipal.getCbRegion().getItemAt(0));
        ventanaPrincipal.getTxAnoFundacion().setText("");
        ventanaPrincipal.getsMSICantidad().setValue(0);
        ventanaPrincipal.getsWorldsCantidad().setValue(0);
        ventanaPrincipal.getsLigaCantidad().setValue(0);
        ventanaPrincipal.getTxEntrenador().setText("");
        ventanaPrincipal.getTxJugadoresxEquipo().setText("");
    }

    public void limpiarJugador(){
        ventanaPrincipal.getsJugadorID().setValue(0);
        ventanaPrincipal.getTxNombreJugador().setText("");
        ventanaPrincipal.setCbPosicion((String) ventanaPrincipal.getCbPosicion().getItemAt(0));
        ventanaPrincipal.getTxNacionalidad().setText("");
        ventanaPrincipal.getTxKDA().setText("");
        ventanaPrincipal.getTxCSxMin().setText("");
        ventanaPrincipal.getTxPartiKills().setText("");
        ventanaPrincipal.getsEquipoxJugador().setValue(0);
    }

    private void migrarData() throws Exception {
        DataMigration.migrateData();
    }

    public void ejecutarScript() {
        SQLiteDAO sqLiteDAO = new SQLiteDAOImpl();

        sqLiteDAO.ejecutarScript();
    }

    public void abrirDialogoConsultasAvanzadas() {
        // Crear una instancia del diálogo
        ConsultasAvanzadas dialogo = new ConsultasAvanzadas();

        //Instancia de su controlador, antes de que hacer visible para registrar los listeners
        ControladorConsultas controladorConsultas = new ControladorConsultas(dialogo);

        // Configurar el tamaño antes de hacerlo visible
        dialogo.setSize(1000, 450);

        // Mostrar el diálogo
        dialogo.setVisible(true);
    }



    //METHODS AUXILIARES
    private Equipo obtenerEquipo() {
        int id = (int) ventanaPrincipal.getsEquipoID().getValue();
        String nombre = ventanaPrincipal.getTxNombreEquipo().getText();
        String region = (String) ventanaPrincipal.getCbRegion().getSelectedItem();
        int anoFundacion = Integer.parseInt(ventanaPrincipal.getTxAnoFundacion().getText());

        List<Titulo> listaTitulos = new ArrayList<>();
        Titulo msi = new Titulo();
        msi.setTipo("MSI");
        msi.setCantidad((int) ventanaPrincipal.getsMSICantidad().getValue());
        Titulo worlds = new Titulo();
        worlds.setTipo("Worlds");
        worlds.setCantidad((int) ventanaPrincipal.getsWorldsCantidad().getValue());
        Titulo liga = new Titulo();
        liga.setTipo("Liga Nacional");
        liga.setCantidad((int) ventanaPrincipal.getsLigaCantidad().getValue());
        listaTitulos.add(msi);
        listaTitulos.add(worlds);
        listaTitulos.add(liga);

        String entrenador = ventanaPrincipal.getTxEntrenador().getText();

        List<Integer> jugadores = new ArrayList<>();
        String[] jugadoresEquipo = ventanaPrincipal.getTxJugadoresxEquipo().getText().split(",");

        for (String jugadorEquipo : jugadoresEquipo) {
            try {
                jugadores.add(Integer.parseInt(jugadorEquipo.trim())); // Convierte a Integer y agrega a la lista
            } catch (NumberFormatException e) {
                // Si no es un número válido, CAMBIAR
                JOptionPane.showMessageDialog(
                        null,
                        "Error: ID no válido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        return new Equipo(id, nombre, region, anoFundacion, listaTitulos, entrenador, jugadores);
    }

    private Jugador obtenerJugador() {
        int id = (int) ventanaPrincipal.getsJugadorID().getValue(); //SPINNER
        String nombre = ventanaPrincipal.getTxNombreJugador().getText();
        String posicion = (String) ventanaPrincipal.getCbPosicion().getSelectedItem();
        String nacionalidad = ventanaPrincipal.getTxNacionalidad().getText();
        Estadisticas estadisticas = new Estadisticas();
        double kda;
        try {
            kda = Double.parseDouble(ventanaPrincipal.getTxKDA().getText());
            estadisticas.setKda(kda);
        } catch (NumberFormatException e) {
            // Maneja el error, por ejemplo, mostrando un mensaje al usuario
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un valor numérico válido para KDA.");
        }
        double csPorMinuto;
        try {
            csPorMinuto = Double.parseDouble(ventanaPrincipal.getTxCSxMin().getText());
            if (csPorMinuto > 0) {
                estadisticas.setCsPorMinuto(csPorMinuto);
            } else {
                estadisticas.setCsPorMinuto(0);
            }

        } catch (NumberFormatException e) {
            // Maneja el error, por ejemplo, mostrando un mensaje al usuario
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un valor numérico válido para CSxMin.");
        }

        String participacionKill = ventanaPrincipal.getTxPartiKills().getText();

        if (participacionKill.contains("%")) {
            estadisticas.setParticipacionKillFormatted(participacionKill);
        } else {
            estadisticas.setParticipacionKill(Integer.parseInt(participacionKill));
        }

        int idEquipo = (int) ventanaPrincipal.getsEquipoxJugador().getValue();

        return new Jugador(id, nombre, posicion, nacionalidad, estadisticas, idEquipo);
    }

    private void seleccionarFilaTablaEquipos() {
        int fila = ventanaPrincipal.getTablaEquipos().getSelectedRow();

        if (fila != -1) {
            int id = Integer.parseInt(ventanaPrincipal.getDtmEquipo().getValueAt(fila, 0).toString());
            String nombre = ventanaPrincipal.getDtmEquipo().getValueAt(fila, 1).toString();
            String region = ventanaPrincipal.getDtmEquipo().getValueAt(fila, 2).toString();
            String anoPublicacion = ventanaPrincipal.getDtmEquipo().getValueAt(fila, 3).toString();

            // Procesar títulos
            String totalTitulos = ventanaPrincipal.getDtmEquipo().getValueAt(fila, 4).toString();
            Map<String, Integer> titulos = procesarTitulos(totalTitulos);

            // Procesar jugadores
            String totalJugadores = ventanaPrincipal.getDtmEquipo().getValueAt(fila, 6).toString();
            String jugadoresString = procesarJugadores(totalJugadores);

            // Asignar valores a los componentes de la vista
            ventanaPrincipal.getsEquipoID().setValue(id);
            ventanaPrincipal.getTxNombreEquipo().setText(nombre);
            ventanaPrincipal.getCbRegion().setSelectedItem(region);
            ventanaPrincipal.getTxAnoFundacion().setText(anoPublicacion);
            ventanaPrincipal.getsMSICantidad().setValue(titulos.getOrDefault("MSI", 0));
            ventanaPrincipal.getsWorldsCantidad().setValue(titulos.getOrDefault("Worlds", 0));
            ventanaPrincipal.getsLigaCantidad().setValue(titulos.getOrDefault("Liga Nacional", 0));
            ventanaPrincipal.getTxEntrenador().setText(ventanaPrincipal.getDtmEquipo().getValueAt(fila, 5).toString());
            ventanaPrincipal.getTxJugadoresxEquipo().setText(jugadoresString);
        }
    }

    private Map<String, Integer> procesarTitulos(String totalTitulos) {
        Map<String, Integer> titulosMap = new HashMap<>();
        String[] titulosPartes = totalTitulos.split(","); // Dividimos por comas

        // MSI (8), Worlds (4), Liga Nacional (3)
        for (String titulo : titulosPartes) {
            // Usamos una expresión regular para identificar el formato "nombre (número)"
            titulo = titulo.trim();
            String regex = "^(.*)\\s*\\((\\d+)\\)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(titulo);

            if (matcher.find()) {
                String tipo = matcher.group(1).trim(); // Nombre del título
                String cantidadStr = matcher.group(2); // Cantidad dentro de paréntesis

                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    titulosMap.put(tipo, cantidad);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al procesar la cantidad para el título: " + tipo,
                            "Error de formato",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Formato inválido para el título: " + titulo,
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        return titulosMap;
    }


    // Method para procesar jugadores
    private String procesarJugadores(String totalJugadores) {
        try {
            return totalJugadores.replaceAll("[\\[\\]]", "").trim();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al procesar la lista de jugadores. Por favor verifica el formato.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );
            return ""; // Valor por defecto en caso de error
        }
    }

    private void seleccionarFilaTablaJugadores() {
        int fila = ventanaPrincipal.getTablaJugadores().getSelectedRow();

        if (fila != -1) {
            int id = Integer.parseInt(ventanaPrincipal.getDtmJugador().getValueAt(fila, 0).toString());
            String nombre = ventanaPrincipal.getDtmJugador().getValueAt(fila, 1).toString();
            String posicion = ventanaPrincipal.getDtmJugador().getValueAt(fila, 2).toString();
            String nacionalidad = ventanaPrincipal.getDtmJugador().getValueAt(fila, 3).toString();
            String kda = ventanaPrincipal.getDtmJugador().getValueAt(fila, 4).toString();
            String csPorMin = ventanaPrincipal.getDtmJugador().getValueAt(fila, 5).toString();
            //REVISAR EL %
            String participacionKills = ventanaPrincipal.getDtmJugador().getValueAt(fila, 6).toString().replace("%", "");
            int idEquipo = Integer.parseInt(ventanaPrincipal.getDtmJugador().getValueAt(fila, 7).toString());


            // Asignar valores a los componentes de la vista
            ventanaPrincipal.getsJugadorID().setValue(id);
            ventanaPrincipal.getTxNombreJugador().setText(nombre);
            ventanaPrincipal.getCbPosicion().setSelectedItem(posicion);
            ventanaPrincipal.getTxNacionalidad().setText(nacionalidad);
            ventanaPrincipal.getTxKDA().setText(kda);
            ventanaPrincipal.getTxCSxMin().setText(csPorMin);
            ventanaPrincipal.getTxPartiKills().setText(participacionKills);
            ventanaPrincipal.getsEquipoxJugador().setValue(idEquipo);
        }
    }
}
