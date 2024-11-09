package org.dmp.repositorio;

import org.dmp.modelo.Alumno;

import java.io.File;
import java.util.List;

/**
 * @author danielpm.dev
 */
public interface AlumnoDAO {

    /**
     * Conecta a la base de datos Oracle con las credenciales adecuadas.
     *
     * @return true si la conexión es exitosa; false en caso contrario.
     */
    public boolean conectarOracle();

    /**
     * Conecta a la base de datos SQLite con las credenciales adecuadas.
     *
     * @return true si la conexión es exitosa; false en caso contrario.
     */
    public boolean conectarSQLite();

    /**
     * Importa datos desde un archivo JSON o XML a la base de datos.
     *
     * @param archivo Archivo JSON o XML.
     * @param formato Formato del archivo, puede ser "JSON" o "XML".
     * @return lista de alumnos que contiene el archivo.
     */
    public List<Alumno> importarDatos(File archivo, String formato);

    /**
     * Exporta datos de la base de datos a un archivo JSON o XML.
     *
     * @param archivo Ruta del archivo JSON o XML de destino.
     * @param formato Formato del archivo, puede ser "JSON" o "XML".
     * @return true si la exportación es exitosa; false en caso contrario.
     */
    public boolean exportarDatos(List<Alumno> listaAlumnos, File archivo, String formato);

    /**
     * Consulta un alumno por clave primaria.
     *
     * @param nombreAlumno Nombre del alumno como clave de búsqueda.
     * @return true si el alumno existe; false si no se encuentra en la base de datos.
     */
    public List<Alumno> consultarPorApellidos(String nombreAlumno);

    /**
     * Consulta alumnos según un atributo específico.
     *
     * @param atributo Nombre del atributo para la consulta.
     * @param valor    Valor del atributo para la consulta.
     * @return true si se encuentran registros que coinciden con el atributo y valor especificados; false en caso contrario.
     */
    public List<Alumno> consultarPorAtributo(String atributo, String valor);

    /**
     * Inserta un nuevo registro de alumno en la base de datos.
     *
     * @param alumno Objeto Alumno con los datos a insertar.
     * @return Número de filas afectadas (1 si se inserta correctamente; 0 en caso contrario).
     */
    public boolean insertarAlumno(Alumno alumno);

    /**
     * Actualiza los datos de un alumno existente en la base de datos.
     *
     * @param alumno Objeto Alumno con los datos actualizados.
     * @return Número de filas afectadas (1 si la actualización es exitosa; 0 en caso contrario).
     */
    public boolean actualizarAlumno(Alumno alumno);

    /**
     * Elimina un alumno de la base de datos según el nombre.
     *
     * @param nombreAlumno    Nombre del alumno a eliminar.
     * @param apellidosAlumno Apellidos del alumno a eliminar.
     * @return Número de filas afectadas (1 si la eliminación es exitosa; 0 en caso contrario).
     */
    public boolean eliminarAlumno(String nombreAlumno, String apellidosAlumno);

    /**
     * Ejecuta un script SQL completo desde un archivo.
     *
     * @param archivo Ruta del archivo SQL a ejecutar.
     * @return true si la ejecución del script es exitosa; false en caso contrario.
     */
    public boolean ejecutarScript(String archivo);

    /**
     * Obtiene el historial de todas las sentencias SQL ejecutadas.
     *
     * @return Lista de cadenas con cada sentencia SQL ejecutada.
     */
    public List<String> obtenerHistorial();

    /**
     * Guarda el historial de sentencias ejecutadas en un archivo especificado.
     *
     * @param archivo Ruta del archivo de destino donde se guardará el historial.
     * @return true si se guarda correctamente; false en caso contrario.
     */
    public boolean guardarHistorial(String archivo);

    /**
     * Carga un historial de sentencias SQL ejecutadas desde un archivo.
     *
     * @param archivo Ruta del archivo desde el cual cargar el historial.
     * @return true si la carga es exitosa; false en caso contrario.
     */
    public boolean cargarHistorial(String archivo);

    /**
     * Importa datos desde un archivo JSON a la base de datos.
     *
     * @param archivo Archivo JSON.
     * @return lista de alumnos que contiene el archivo JSON.
     */
    public List<Alumno> importarDatosDesdeJSON(File archivo);

    /**
     * Exporta datos de la base de datos a un archivo JSON.
     *
     * @param listaAlumnos Lista de alumnos a exportar.
     * @param archivo Ruta del archivo JSON de destino.
     * @return true si la exportación es exitosa; false en caso contrario.
     */
    public boolean exportarDatosAJSON(List<Alumno> listaAlumnos, File archivo);

    /**
     * Importa datos desde un archivo XML a la base de datos.
     *
     * @param archivo Archivo XML.
     * @return lista de alumnos que contiene el archivo XML.
     */
    public List<Alumno> importarDatosDesdeXML(File archivo);

    /**
     * Exporta datos de la base de datos a un archivo XML.
     *
     * @param listaAlumnos Lista de alumnos a exportar.
     * @param archivo Ruta del archivo XML de destino.
     * @return true si la exportación es exitosa; false en caso contrario.
     */
    public boolean exportarDatosAXML(List<Alumno> listaAlumnos, File archivo);

    /**
     * Devuelve los datos que hay en la tabla Alumnos
     *
     * @return La lista de la tabla Alumnos
     */
    public List<Alumno> mostrarAlumnos ();
}
