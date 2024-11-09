package org.dmp.controlador;

import org.dmp.modelo.Alumno;
import org.dmp.modelo.Hobby;
import org.dmp.modelo.ModeloAlumno;
import org.dmp.repositorio.AlumnoDAO;
import org.dmp.repositorio.AlumnoDAOImpl;
import org.dmp.servicios.Servicios;
import org.dmp.servicios.ServiciosImplSQL;
import org.dmp.util.Convertio;
import org.dmp.servicios.ServiciosImpl;
import org.dmp.vistas.Ventana;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * La clase {@link Controlador} actúa como el intermediario entre la vista {@link Ventana}
 * y el modelo {@link ModeloAlumno}. Se encarga de gestionar la lógica de la aplicación,
 * procesar las interacciones del usuario y coordinar la comunicación entre la interfaz
 * gráfica y los servicios de negocio.
 *
 * <p>El controlador maneja las acciones del usuario al interactuar con los componentes
 * de la interfaz de usuario, como botones y tablas. Además, permite cargar y guardar
 * información de los alumnos en diferentes formatos (binario, CSV, TXT), así como
 * realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) sobre los datos de los alumnos.</p>
 *
 * <p>También se encarga de la visualización de los datos seleccionados en la tabla,
 * actualizando los campos de la vista con la información del alumno seleccionado.</p>
 */
public class Controlador {

    private Ventana vista;
    private ModeloAlumno listaAlumnos;
    AlumnoDAO alumnoDAO;
    //private ConversionFicheros conversionFicheros;

    /**
     * Constructor de la clase {@link Controlador}.
     * <p>
     * Este constructor inicializa la instancia del controlador, vinculando la vista y el modelo de alumnos
     * proporcionados. También se configuran los listeners para los componentes de la interfaz gráfica
     * de usuario (GUI) de la vista, permitiendo que se realicen acciones como añadir, borrar,
     * actualizar y consultar alumnos. Además, se establecen listeners para cargar y guardar datos
     * de alumnos en diferentes formatos (binario, CSV, TXT).
     * <p>
     * Cuando se hace clic en la tabla de contenido, se recuperan los datos de la fila seleccionada
     * y se actualizan los campos correspondientes en la vista. Esto incluye el nombre, apellidos,
     * correo, teléfono, localidad, estudios, fecha de nacimiento, ciclo, nivel de ordenador,
     * carnet, motivación y hobbies del alumno.
     *
     * @param vista        La instancia de la vista {@link Ventana} que se utilizará para la interacción
     *                     con el usuario.
     * @param listaAlumnos La instancia del modelo {@link ModeloAlumno} que contiene la lista de
     *                     alumnos y la lógica de negocio asociada.
     */
    public Controlador(Ventana vista, ModeloAlumno listaAlumnos) {
        this.vista = vista;
        this.listaAlumnos = listaAlumnos;
        this.alumnoDAO = new AlumnoDAOImpl();

        this.vista.getAñadirButton().addActionListener(e -> añadirAlumno());
        this.vista.getBorrarButton().addActionListener(e -> borrarAlumno());
        this.vista.getActualizarAlumnoButton().addActionListener(e -> actualizarAlumno());
        this.vista.getConsultarButton().addActionListener(e -> consultarAlumno());
        this.vista.getLimpiarButton().addActionListener(e -> limpiarCampos());
        this.vista.getLimpiarTextAreaButton().addActionListener(e -> limpiarTextArea());
        this.vista.getBuscarApellidosButton().addActionListener(e -> buscarApellidos());

        this.vista.getCargarAlumnosButton().addActionListener(e -> cargarAlumnosBinario());
        this.vista.getGuardarAlumnosButton().addActionListener(e -> guardarAlumnosBinario());
        this.vista.getCargarDesdeCSVButton().addActionListener(e -> cargarAlumnosCSV());
        this.vista.getGuardarEnCSVButton().addActionListener(e -> guardarAlumnosCSV());
        this.vista.getGuardarTXTButton().addActionListener(e -> guardarAlumnosTXT());
        this.vista.getGuardarEnJSONButton().addActionListener(e -> guardarAlumnosJson());
        this.vista.getGuardarEnXMLButton().addActionListener(e -> guardarAlumnosXML());
        this.vista.getCargarDesdeJSONButton().addActionListener(e -> cargarAlumnosJson());
        this.vista.getCargarDesdeXMLButton().addActionListener(e -> cargarAlumnosXML());

        this.vista.getCargarFicherosButton().addActionListener(e -> mostrarArchivos());
        this.vista.getMostrarListaButton().addActionListener(e -> cargarLista());

        this.vista.getTableContenido().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFilaTabla();
            }
        });

        this.vista.getListArchivos().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Solo actuamos cuando el evento no está ajustando (evitar doble invocación)
                if (!e.getValueIsAdjusting()) {
                    mostrarContenidoArchivo();
                }
            }
        });
    }

    /**
     * Muestra una lista de archivos desde una carpeta específica y sus tamaños en el modelo de lista de la interfaz.
     * Los archivos se obtienen desde la carpeta "../GestionAlumnos/archivos".
     * Cada archivo se muestra junto con su tamaño en bytes, KB o MB, dependiendo del tamaño.
     * <p>
     * El contenido de la lista de archivos se limpia antes de agregar nuevos elementos.
     * Si la ruta especificada no es una carpeta válida, se muestra un mensaje de error.
     */
    private void mostrarArchivos() {
        // Ruta de la carpeta
        File carpeta = new File("../GestionAlumnos/archivos");

        // Comprobamos que es una carpeta y obtenemos la lista de archivos
        if (carpeta.isDirectory()) {
            // Obtenemos los archivos
            File[] archivos = carpeta.listFiles();

            // Limpiamos el modelo de lista antes de añadir nuevos elementos
            vista.getDlm().clear();

            // Añadimos los archivos al modelo de lista con su tamaño
            if (archivos != null) {
                Arrays.stream(archivos).forEach(archivo -> {
                    if (archivo.isFile()) {
                        // Obtenemos el nombre y el tamaño del archivo
                        String nombreArchivo = archivo.getName();
                        long tamañoArchivo = archivo.length(); // Tamaño en bytes

                        // Formateamos el tamaño en KB o MB para mayor legibilidad
                        String tamañoFormateado;
                        if (tamañoArchivo >= 1024 * 1024) {
                            tamañoFormateado = String.format("%.2f MB", tamañoArchivo / (1024.0 * 1024.0));
                        } else if (tamañoArchivo >= 1024) {
                            tamañoFormateado = String.format("%.2f KB", tamañoArchivo / 1024.0);
                        } else {
                            tamañoFormateado = tamañoArchivo + " bytes";
                        }

                        // Añadimos el archivo con su tamaño al modelo
                        vista.getDlm().addElement(nombreArchivo + " (" + tamañoFormateado + ")");
                    }
                });
            }
        } else {
            System.out.println("No es una carpeta válida.");
        }
    }

    /**
     * Muestra el contenido de un archivo seleccionado en el JTextArea de la interfaz.
     * El archivo debe estar ubicado en la carpeta "../GestionAlumnos/archivos".
     * Si el nombre del archivo contiene el tamaño (entre paréntesis), esta información
     * será eliminada antes de acceder al archivo.
     * <p>
     * El contenido del archivo se lee completamente y se muestra en el JTextArea.
     * En caso de error al leer el archivo, se muestra un mensaje de error en el JTextArea.
     */
    private void mostrarContenidoArchivo() {
        // Obtener el archivo seleccionado
        String archivoSeleccionado = (String) vista.getListArchivos().getSelectedValue();

        if (archivoSeleccionado != null) {
            // Eliminar el tamaño del nombre del archivo (si existe entre paréntesis)
            if (archivoSeleccionado.contains("(")) {
                archivoSeleccionado = archivoSeleccionado.substring(0, archivoSeleccionado.lastIndexOf(" (")).trim();
            }

            // Ruta del archivo
            String rutaArchivo = "../GestionAlumnos/archivos/" + archivoSeleccionado;

            try {
                // Leer todo el contenido del archivo
                String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
                // Mostrar el contenido en el JTextArea
                vista.getTxaContenidoArchivos().setText(contenido);
            } catch (IOException ex) {
                ex.printStackTrace();
                vista.getTxaContenidoArchivos().setText("Error al leer el archivo.");
            }
        }
    }

    /**
     * Añade un nuevo alumno a la lista de alumnos.
     * Recupera los datos del alumno de la interfaz de usuario
     * y los utiliza para crear un objeto de la clase Alumno.
     * A continuación, el alumno se añade a la lista
     * de alumnos utilizando el servicio correspondiente.
     */
    public void añadirAlumno() {
        String nombre = vista.getTfNombre().getText();
        String ape = vista.getTfApellidos().getText();
        String correo = vista.getTfCorreo().getText();
        String tel = vista.getTfTelefono().getText();
        String localidad = vista.getTfLocalidad().getText();
        String estudios = (String) vista.getCbEstudios().getSelectedItem(); //ComboBox
        LocalDate fechaNacimiento = LocalDate.parse(vista.getTfFechaNacimiento().getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ciclo = (String) vista.getCbCiclo().getSelectedItem();
        int ordenador = vista.getNivelOrdenador();
        String carnet = vista.getRbCarnet();
        String motivacion = (String) vista.getCbMotivacion().getSelectedItem();
        ArrayList<Hobby> hobbies;
        hobbies = obtenerHobbies();
        Alumno alumno = new Alumno(nombre, ape, correo, tel, localidad, estudios, fechaNacimiento, ciclo, ordenador, carnet, motivacion, hobbies);
        listaAlumnos.getServicios().añadirAlumno(alumno);
        mostrarAlumnos();
    }

    /**
     * Borra un nuevo alumno a la lista de alumnos.
     * Este método recupera los datos del alumno de la interfaz de usuario
     * y los utiliza para crear un objeto de la clase Alumno.
     * A continuación, se usa el método correspondiente
     */
    public void borrarAlumno() {
        String nombre = vista.getTfNombre().getText();
        String ape = vista.getTfApellidos().getText();
        String correo = vista.getTfCorreo().getText();
        String tel = vista.getTfTelefono().getText();
        String localidad = vista.getTfLocalidad().getText();
        String estudios = (String) vista.getCbEstudios().getSelectedItem(); //ComboBox
        LocalDate fechaNacimiento = LocalDate.parse(vista.getTfFechaNacimiento().getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ciclo = (String) vista.getCbCiclo().getSelectedItem();
        int ordenador = vista.getNivelOrdenador();
        String carnet = vista.getRbCarnet();
        String motivacion = (String) vista.getCbMotivacion().getSelectedItem();
        ArrayList<Hobby> hobbies;
        hobbies = obtenerHobbies();
        Alumno alumno = new Alumno(nombre, ape, correo, tel, localidad, estudios, fechaNacimiento, ciclo, ordenador, carnet, motivacion, hobbies);
        listaAlumnos.getServicios().borrarAlumno(alumno);
        mostrarAlumnos();
    }

    /**
     * Actualiza un nuevo alumno a la lista de alumnos.
     * Este método recupera los datos del alumno de la interfaz de usuario
     * y los utiliza para crear un objeto de la clase Alumno.
     * A continuación, se usa el método correspondiente
     */
    public void actualizarAlumno() {
        String nombre = vista.getTfNombre().getText();
        String ape = vista.getTfApellidos().getText();
        String correo = vista.getTfCorreo().getText();
        String tel = vista.getTfTelefono().getText();
        String localidad = vista.getTfLocalidad().getText();
        String estudios = (String) vista.getCbEstudios().getSelectedItem(); //ComboBox
        LocalDate fechaNacimiento = LocalDate.parse(vista.getTfFechaNacimiento().getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ciclo = (String) vista.getCbCiclo().getSelectedItem();
        int ordenador = vista.getNivelOrdenador();
        String carnet = vista.getRbCarnet();
        String motivacion = (String) vista.getCbMotivacion().getSelectedItem();
        ArrayList<Hobby> hobbies;
        hobbies = obtenerHobbies();
        Alumno alumno = new Alumno(nombre, ape, correo, tel, localidad, estudios, fechaNacimiento, ciclo, ordenador, carnet, motivacion, hobbies);
        listaAlumnos.getServicios().actualizarAlumno(alumno);
        mostrarAlumnos();
    }

    /**
     * Busca por apellidos alumnos que correspondan.
     * Este método recupera los datos del alumno de la interfaz de usuario
     * A continuación, se usa el método correspondiente
     */
    public void buscarApellidos() {
        String apellidos = vista.getTfApellidos().getText();
        //listaAlumnos.getServicios().buscarAlumnoApellidos(apellidos);
        listaAlumnos.setListaAlumnos(listaAlumnos.getServicios().buscarAlumnoApellidos(apellidos));
        mostrarAlumnos();
    }

    /**
     * Busca filtra por el campo correspondiente
     * Recupera el criterio de la vista y asigna un valor
     * A continuación, se usa el método correspondiente
     */
    public void consultarAlumno() {
        String criterio = (String) vista.getCbFiltro().getSelectedItem();
        String valor;

        switch (criterio) {
            case "Nombre" -> valor = vista.getTfNombre().getText();
            case "Localidad" -> valor = vista.getTfLocalidad().getText();
            case "Estudios" -> valor = (String) vista.getCbEstudios().getSelectedItem();
            case "Carnet" -> valor = vista.getRbCarnet();
            default -> valor = "";
        }

        listaAlumnos.setListaAlumnos(listaAlumnos.getServicios().filtrarAlumnos(criterio, valor));
        //listaAlumnos.getServicios().filtrarAlumnos(criterio, valor);
        mostrarAlumnos();
    }

    /**
     * Carga un fichero binario mediante un JFileChooser
     * y una comprobación para que el usuario conozca los riesgos.
     * Cargandolos en una lista y esta pasandola a la lista que usa el programa
     */
    public void cargarAlumnosBinario() {
        int respuesta = JOptionPane.showConfirmDialog(null,
                "Advertencia: Cargar un nuevo archivo sobrescribirá la información actual. ¿Deseas continuar?",
                "Advertencia de Carga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            // Mostrar el JFileChooser para cargar el archivo
            File archivo = mostrarFileChooser(JFileChooser.OPEN_DIALOG, "Cargar archivo", "dat");
            List<Alumno> listaCargada = null;

            if (archivo != null) {
                listaCargada = listaAlumnos.getServicios().cargarAlumnos(archivo);

                // Verificar si la lista cargada no es nula antes de asignar
                if (listaCargada != null) {
                    listaAlumnos.setListaAlumnos(listaCargada);
                    if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                        ((ServiciosImpl) listaAlumnos.getServicios()).setListaAlumnos(listaCargada);
                    }
                    mostrarAlumnos();
                }
            }
        } else {
            System.out.println("La carga fue cancelada por el usuario.");
        }
    }

    /**
     * Guarda un fichero binario mediante un JFileChooser
     * y una comprobación para que el usuario conozca los riesgos.
     * Guardandolos en fichero con el nombre que elija el usuario
     */
    public void guardarAlumnosBinario() {
        File archivo = mostrarFileChooser(JFileChooser.SAVE_DIALOG, "Guardar archivo", "dat");

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
            listaAlumnos.getServicios().guardarAlumnos(listaAlumnos.getListaAlumnos(), archivo);
        }
    }

    /**
     * Carga una lista de alumnos desde un archivo CSV.
     * <p>
     * Este método solicita al usuario confirmación antes de cargar un nuevo archivo.
     * Si el usuario acepta, se abre un JFileChooser para seleccionar el archivo CSV
     * que contiene la información de los alumnos. Una vez seleccionado el archivo,
     * se carga la lista de alumnos utilizando el servicio correspondiente. Si la carga
     * es exitosa, se actualiza la lista de alumnos en la aplicación y se muestra
     * la lista cargada. Si el usuario cancela la operación, se imprime un mensaje
     * en la consola indicando que la carga fue cancelada.
     */
    public void cargarAlumnosCSV() {
        int respuesta = JOptionPane.showConfirmDialog(null,
                "Advertencia: Cargar un nuevo archivo sobrescribirá la información actual. ¿Deseas continuar?",
                "Advertencia de Carga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            // Mostrar el JFileChooser para cargar el archivo
            File archivo = mostrarFileChooser(JFileChooser.OPEN_DIALOG, "Cargar archivo", "csv");
            List<Alumno> listaCargada = null;

            if (archivo != null) {
                listaCargada = listaAlumnos.getServicios().cargarAlumnosCSV(archivo);

                // Verificar si la lista cargada no es nula antes de asignar
                if (listaCargada != null) {
                    listaAlumnos.setListaAlumnos(listaCargada);
                    if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                        ((ServiciosImpl) listaAlumnos.getServicios()).setListaAlumnos(listaCargada);
                    }
                    mostrarAlumnos();
                }
            }
        } else {
            System.out.println("La carga fue cancelada por el usuario.");
        }

        //File archivo = new File("./archivos/alumnosCrudo.csv");
        //List<Alumno> listaCargada = listaAlumnos.getServicios().cargarAlumnosCSV(archivo);
    }

    /**
     * Guarda un fichero CSV mediante un JFileChooser
     * y una comprobación para que el usuario conozca los riesgos.
     * Guardandolos en fichero con el nombre que elija el usuario
     */
    public void guardarAlumnosCSV() {
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
            listaAlumnos.getServicios().guardarAlumnosCSV(listaAlumnos.getListaAlumnos(), archivo);
        }

        //File archivo = new File("./archivos/alumnosExportadoCSV.csv");
        //listaAlumnos.getServicios().guardarAlumnosCSV(listaAlumnos.getListaAlumnos(), archivo);
    }

    /**
     * Guarda un fichero TXT mediante un JFileChooser
     * y una comprobación para que el usuario conozca los riesgos.
     * Guardandolos en fichero con el nombre que elija el usuario.
     */
    public void guardarAlumnosTXT() {
        File archivo = mostrarFileChooser(JFileChooser.SAVE_DIALOG, "Guardar archivo", "txt");

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
            listaAlumnos.getServicios().guardarAlumnosTXT(listaAlumnos.getListaAlumnos(), archivo);
        }
        //File archivo = new File("./archivos/consultas.txt");
        //listaAlumnos.getServicios().guardarAlumnosTXT(listaAlumnos.getListaAlumnos(), archivo);
    }

    /**
     * Guarda la lista de alumnos en un archivo JSON utilizando un FileChooser.
     * <p>
     * Este método abre un cuadro de diálogo para seleccionar la ubicación donde se
     * guardará el archivo JSON. Si el archivo seleccionado ya existe, se muestra
     * un cuadro de diálogo para confirmar si se desea sobrescribir. En caso
     * afirmativo, convierte la lista de alumnos a formato JSON y la guarda en el archivo
     * seleccionado.
     * </p>
     */
    public void guardarAlumnosJson() {
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

            if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                Convertio.guardarAlumnosEnJson(listaAlumnos.getListaAlumnos(), archivo);
            } else if (listaAlumnos.getServicios() instanceof ServiciosImplSQL) {
                alumnoDAO.exportarDatosAJSON(listaAlumnos.getListaAlumnos(), archivo);
            }
        }
    }

    //@see ConversionFicheros#jsonToObject(File)

    /**
     * Carga una lista de alumnos desde un archivo JSON utilizando un FileChooser.
     * <p>
     * Este método muestra un cuadro de diálogo de confirmación antes de sobrescribir
     * la lista de alumnos actual con una nueva lista cargada desde un archivo JSON.
     * Si el usuario acepta, se abre un cuadro de diálogo para seleccionar el archivo,
     * luego se convierte el contenido JSON en una lista de objetos {@code Alumno} y se
     * actualiza la lista de alumnos actual.
     * </p>
     */
    public void cargarAlumnosJson() {
        int respuesta = JOptionPane.showConfirmDialog(null,
                "Advertencia: Cargar un nuevo archivo sobrescribirá la información actual. ¿Deseas continuar?",
                "Advertencia de Carga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            // Mostrar el JFileChooser para cargar el archivo
            File archivo = mostrarFileChooser(JFileChooser.OPEN_DIALOG, "Cargar archivo", "json");
            List<Alumno> listaCargada = null;

            if (archivo != null) {
                if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                    listaCargada = Convertio.cargarAlumnosDesdeJson(archivo);
                } else if (listaAlumnos.getServicios() instanceof ServiciosImplSQL) {
                    listaCargada = alumnoDAO.importarDatosDesdeJSON(archivo);
                }

                // Verificar si la lista cargada no es nula antes de asignar
                if (listaCargada != null) {
                    listaAlumnos.setListaAlumnos(listaCargada);
                    if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                        ((ServiciosImpl) listaAlumnos.getServicios()).setListaAlumnos(listaCargada);
                    } else if (listaAlumnos.getServicios() instanceof ServiciosImplSQL) {
                        ((ServiciosImplSQL) listaAlumnos.getServicios()).setListaAlumnos(listaCargada);
                    }
                    mostrarAlumnos();
                }
            }
        } else {
            System.out.println("La carga fue cancelada por el usuario.");
        }
    }

    /**
     * Guarda la lista de alumnos en un archivo XML utilizando un FileChooser.
     * <p>
     * Este método abre un cuadro de diálogo para seleccionar la ubicación donde se
     * guardará el archivo XML. Si el archivo seleccionado ya existe, se muestra un
     * cuadro de diálogo para confirmar si se desea sobrescribir. En caso afirmativo,
     * convierte la lista de alumnos a formato XML y la guarda en el archivo seleccionado.
     * </p>
     */
    public void guardarAlumnosXML() {
        File archivo = mostrarFileChooser(JFileChooser.SAVE_DIALOG, "Guardar archivo", "xml");

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
            if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                Convertio.guardarAlumnosEnXml(listaAlumnos.getListaAlumnos(), archivo);
            } else if (listaAlumnos.getServicios() instanceof ServiciosImplSQL) {
                alumnoDAO.exportarDatosAXML(listaAlumnos.getListaAlumnos(), archivo);
            }
        }
    }

    /**
     * Carga una lista de alumnos desde un archivo XML utilizando un FileChooser.
     * <p>
     * Este método muestra un cuadro de diálogo de confirmación antes de sobrescribir
     * la lista de alumnos actual con una nueva lista cargada desde un archivo XML.
     * Si el usuario acepta, se abre un cuadro de diálogo para seleccionar el archivo,
     * luego se convierte el contenido XML en una lista de objetos {@code Alumno} y se
     * actualiza la lista de alumnos actual.
     * </p>
     */
    public void cargarAlumnosXML() {
        int respuesta = JOptionPane.showConfirmDialog(null,
                "Advertencia: Cargar un nuevo archivo sobrescribirá la información actual. ¿Deseas continuar?",
                "Advertencia de Carga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            // Mostrar el JFileChooser para cargar el archivo
            File archivo = mostrarFileChooser(JFileChooser.OPEN_DIALOG, "Cargar archivo", "xml");
            List<Alumno> listaCargada = null;

            if (archivo != null) {
                if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                    listaCargada = Convertio.cargarAlumnosDesdeXml(archivo);
                } else if (listaAlumnos.getServicios() instanceof ServiciosImplSQL) {
                    listaCargada = alumnoDAO.importarDatosDesdeJSON(archivo);
                }

                // Verificar si la lista cargada no es nula antes de asignar
                if (listaCargada != null) {
                    listaAlumnos.setListaAlumnos(listaCargada);
                    if (listaAlumnos.getServicios() instanceof ServiciosImpl) {
                        ((ServiciosImpl) listaAlumnos.getServicios()).setListaAlumnos(listaCargada);
                    } else if (listaAlumnos.getServicios() instanceof ServiciosImplSQL) {
                        ((ServiciosImplSQL) listaAlumnos.getServicios()).setListaAlumnos(listaCargada);
                    }
                    mostrarAlumnos();
                }
            }
        } else {
            System.out.println("La carga fue cancelada por el usuario.");
        }
    }

    public void cargarLista(){
        if (listaAlumnos.getServicios() instanceof ServiciosImpl){
            //HACER OTRA COSA
        } else if (listaAlumnos.getServicios() instanceof  ServiciosImplSQL) {
            List<Alumno> listaCargada = null;
            listaCargada = alumnoDAO.mostrarAlumnos();
            if (listaCargada != null){
                listaAlumnos.setListaAlumnos(listaCargada);
                mostrarAlumnos();
            }
        }
    }
    
    /**
     * Limpia los campos de la vista para facilitar
     * que el usuario introduzca nuevos datos
     */
    public void limpiarCampos() {
        // Limpiar campos de texto
        vista.setTfNombre("");
        vista.setTfApellidos("");
        vista.setTfCorreo("");
        vista.setTfTelefono("");
        vista.setTfLocalidad("");
        vista.setTfFechaNacimiento(""); // O usar LocalDate si se requiere un valor por defecto

        // Restablecer JComboBox a la primera opción (ajusta según necesites)
        vista.setCbEstudios((String) vista.getCbEstudios().getItemAt(0)); // O puedes usar "" si necesitas un valor vacío
        vista.setCbCiclo((String) vista.getCbCiclo().getItemAt(0)); // Ajusta según la lógica que necesites

        // Establecer el JSlider al valor por defecto (ej. 1)
        vista.setNivelOrdenador(1); // Ajusta según el valor por defecto que necesites

        // Limpiar RadioButton
        vista.setRbCarnet("No tengo Carnet"); // O el valor que definas como por defecto

        // Restablecer ComboBox para motivación
        vista.setCbMotivacion((String) vista.getCbMotivacion().getItemAt(0)); // O puedes usar "" si necesitas un valor vacío

        // Limpiar CheckBoxes de hobbies
        vista.setListaHobbies(new ArrayList<>()); // Desmarcar todos los CheckBox
    }

    public void seleccionarFilaTabla() {
        int fila = vista.getTableContenido().getSelectedRow();

        if (fila != -1) {
            String nombre = vista.getDtm().getValueAt(fila, 0).toString();
            String ape = vista.getDtm().getValueAt(fila, 1).toString();
            String correo = vista.getDtm().getValueAt(fila, 2).toString();
            String telefono = vista.getDtm().getValueAt(fila, 3).toString();
            String localidad = vista.getDtm().getValueAt(fila, 4).toString();
            String estudios = vista.getDtm().getValueAt(fila, 5).toString();
            LocalDate fechaNacimiento = LocalDate.parse(vista.getDtm().getValueAt(fila, 6).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String ciclo = vista.getDtm().getValueAt(fila, 7).toString();
            int ordenador = Integer.parseInt(vista.getDtm().getValueAt(fila, 8).toString());
            String carnet = vista.getDtm().getValueAt(fila, 9).toString();
            String motivacion = vista.getDtm().getValueAt(fila, 10).toString();
            String cadenaHobbies = vista.getDtm().getValueAt(fila, 11).toString(); // Asegúrate de usar el índice correcto

            ArrayList<Hobby> hobbies = mostrarHobbies(cadenaHobbies);

            // Asignar valores a los componentes de la vista
            vista.setTfNombre(nombre);
            vista.setTfApellidos(ape);
            vista.setTfCorreo(correo);
            vista.setTfTelefono(telefono);
            vista.setTfLocalidad(localidad);
            vista.setCbEstudios(estudios); // ComboBox
            vista.setTfFechaNacimiento(fechaNacimiento.toString()); // Convertimos LocalDate a String
            vista.setCbCiclo(ciclo); // ComboBox
            vista.setNivelOrdenador(ordenador); // JSlider
            vista.setRbCarnet(carnet); // RadioButton
            vista.setCbMotivacion(motivacion); // ComboBox
            vista.setListaHobbies(hobbies); // Aquí asignas la lista de hobbies
        }
    }

    /**
     * Muestra en el dtm los datos asociados en la
     * lista de Alumnos
     */
    public void mostrarAlumnos() {
        vista.getDtm().setRowCount(0);

        for (Alumno a : listaAlumnos.getListaAlumnos()) {
            Object[] fila = new Object[12]; // 12 Campos
            fila[0] = a.getNombre();
            fila[1] = a.getApellidos();
            fila[2] = a.getMail();
            fila[3] = a.getTelefono();
            fila[4] = a.getLocalidad();
            fila[5] = a.getEstudios();
            fila[6] = a.getFechaNacimiento();
            fila[7] = a.getCiclo();
            fila[8] = a.getNivelOrdenador();
            fila[9] = a.getCarnet();
            fila[10] = a.getMotivacion();
            fila[11] = a.getHobbies();

            vista.getDtm().addRow(fila);
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
        fileChooser.setCurrentDirectory(new File("../GestionAlumnos/archivos"));

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

    /**
     * Limpia el contenido del JTextArea de la interfaz.
     * Establece el texto del JTextArea en una cadena vacía,
     * eliminando cualquier contenido previamente visible.
     */
    public void limpiarTextArea() {
        vista.getTxaContenidoArchivos().setText(""); // Limpia el JTextArea
    }

    /**
     * Va recorriendo los CheckBox de la vista y si están seleccionados
     * los añade a la lista de Hobbies
     *
     * @return la lista de los hobbies
     */
    public ArrayList<Hobby> obtenerHobbies() {
        ArrayList<Hobby> hobbies = new ArrayList<>();

        // Verificar cuáles CheckBox están seleccionados y añadirlos a la lista de hobbies
        if (vista.getCbColeccionar().isSelected()) {
            hobbies.add(Hobby.COLECCIONAR);
        }
        if (vista.getLecturaCheckBox().isSelected()) {
            hobbies.add(Hobby.LECTURA);
        }
        if (vista.getDeporteCheckBox().isSelected()) {
            hobbies.add(Hobby.DEPORTE);
        }
        if (vista.getJardineriaCheckBox().isSelected()) {
            hobbies.add(Hobby.JARDINERIA);
        }
        if (vista.getMusicaCheckBox().isSelected()) {
            hobbies.add(Hobby.MUSICA);
        }
        if (vista.getVideojuegosCheckBox().isSelected()) {
            hobbies.add(Hobby.VIDEOJUEGOS);
        }
        if (vista.getSenderismoCheckBox().isSelected()) {
            hobbies.add(Hobby.SENDERISMO);
        }
        if (vista.getVoluntariadoCheckBox().isSelected()) {
            hobbies.add(Hobby.VOLUNTARIADO);
        }
        if (vista.getPinturaEsculturaCheckBox().isSelected()) {
            hobbies.add(Hobby.PINTURA_ESCULTURA);
        }
        if (vista.getFotografiaCheckBox().isSelected()) {
            hobbies.add(Hobby.FOTOGRAFIA);
        }
        if (vista.getOtrosCheckBox().isSelected()) {
            hobbies.add(Hobby.OTROS);
        }

        return hobbies;
    }

    /**
     * Convierte una cadena de texto que representa una lista de hobbies en un ArrayList de objetos {@link Hobby}.
     * <p>
     * Este método toma una cadena que contiene nombres de hobbies, los procesa para eliminar
     * corchetes y separa cada hobby por comas. Luego, convierte cada nombre en un objeto de tipo
     * {@link Hobby}. Si se encuentra un nombre que no corresponde a un valor válido en el enum
     * {@link Hobby}, se captura una excepción {@link IllegalArgumentException} y se imprime un mensaje
     * de error en la consola.
     *
     * @param cadenaHobbies La cadena que contiene los nombres de los hobbies en el formato
     *                      "[HOBBY1, HOBBY2, HOBBY3]".
     * @return Un ArrayList de objetos {@link Hobby} que representan los hobbies válidos
     * extraídos de la cadena.
     */
    public static ArrayList<Hobby> mostrarHobbies(String cadenaHobbies) {
        ArrayList<Hobby> hobbies = new ArrayList<>();

        // Elimina los corchetes y divide la cadena en partes
        String[] partes = cadenaHobbies.replaceAll("[\\[\\]]", "").split(",\\s*");

        for (String parte : partes) {
            // Convierte cada parte a Hobby
            try {
                hobbies.add(Hobby.valueOf(parte.trim().toUpperCase())); // Asegúrate de que el enum coincida
            } catch (IllegalArgumentException e) {
                // Manejo de errores si el valor no es un hobby válido
                System.out.println("Hobby inválido: " + parte);
            }
        }

        return hobbies;
    }

    /**
     * Convierte una cadena de texto que representa una lista de hobbies en un ArrayList de objetos {@link Hobby}.
     * <p>
     * Este método toma una cadena que contiene nombres de hobbies, separados por punto y coma (;).
     * Luego, convierte cada nombre en un objeto de tipo {@link Hobby}. Si se encuentra un nombre
     * que no corresponde a un valor válido en el enum {@link Hobby}, se captura una excepción
     * {@link IllegalArgumentException} y se imprime un mensaje de error en la consola.
     *
     * @param cadenaHobbies La cadena que contiene los nombres de los hobbies en el formato
     *                      "HOBBY1; HOBBY2; HOBBY3".
     * @return Un ArrayList de objetos {@link Hobby} que representan los hobbies válidos
     * extraídos de la cadena.
     */
    public static ArrayList<Hobby> mostrarHobbiesCSV(String cadenaHobbies) {
        ArrayList<Hobby> hobbies = new ArrayList<>();

        // Divide la cadena en partes utilizando el separador ';'
        String[] partes = cadenaHobbies.split(";\\s*");

        for (String parte : partes) {
            // Convierte cada parte a Hobby
            try {
                hobbies.add(Hobby.valueOf(parte.trim().toUpperCase())); // Asegúrate de que el enum coincida
            } catch (IllegalArgumentException e) {
                // Manejo de errores si el valor no es un hobby válido
                System.out.println("Hobby inválido: " + parte);
            }
        }

        return hobbies;
    }

    public void setListaAlumnos(ModeloAlumno listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
}


/*
JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo");

        // Establecer ruta por defecto
        fileChooser.setCurrentDirectory(new File("../GestionAlumnos/archivos"));

        // Filtrar para que solo permita guardar archivos binarios
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos binarios", "dat"));

        int userSelection = fileChooser.showSaveDialog(null);

        // Si el usuario selecciona un archivo y presiona "Guardar"
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            // Si el archivo no termina con ".dat", lo añadimos
            if (!archivo.getAbsolutePath().endsWith(".dat")) {
                archivo = new File(archivo + ".dat");
            }

            listaAlumnos.getServicios().guardarAlumnos(listaAlumnos.getListaAlumnos(), archivo);
 */