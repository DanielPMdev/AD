package org.dmp.servicios;

import org.dmp.modelo.Alumno;

import java.io.File;
import java.util.List;

public interface Servicios {

    /**
     * Añade el Alumno introducido
     * @param a Alumno a añadir
     * @return True si lo consigue añadir, false en caso contrario
     */
    public boolean añadirAlumno(Alumno a);

    /**
     * Borra el Alumno introducido de la tabla
     * @param a Alumno a borrar
     * @return True si lo consigue borrar, false en caso contrario
     */
    public boolean borrarAlumno(Alumno a);

    /**
     * Actualiza el Alumno introducido
     * @param a Alumno a actualizar
     * @return True si lo consigue actualizar, false en caso contrario
     */
    public boolean actualizarAlumno(Alumno a);

    /**
     * Filtra alumnos mediante un criterio seleccionado
     * @param criterio criterio a buscar seleccionado por el usuario
     * @param valor valor a buscar dentro del criterio
     * @return List<Alumno> lista de los alumnos que corresponden con ese criterio y valor
     */
    public List<Alumno> filtrarAlumnos(String criterio, String valor);

    /**
     * Busca alumnos por apellidos
     * @param apellidos Apellidos a buscar (NO HACE FALTA COMPLETO)
     * @return List<Alumno> de alumnos que empiecen o contengan el parámetro apellidos
     */
    public List<Alumno> buscarAlumnoApellidos(String apellidos);

    /**
     * Crea un archivo y guarda en él la lista de alumnos
     * pasada por parámetro
     * @param listaAlumnos Lista de alumnos a guardar en el archivo
     * @param archivo Archivo donde se guarda la lista
     */
    public void guardarAlumnos(List<Alumno> listaAlumnos, File archivo);

    /**
     * Crea un archivo CSV y guarda en él la lista de alumnos
     * pasada por parámetro
     * @param listaAlumnos Lista de alumnos a guardar en el archivo
     * @param archivo Archivo donde se guarda la lista
     */
    public void guardarAlumnosCSV(List<Alumno> listaAlumnos, File archivo);

    /**
     * Desde el archivo (bin/dat...) se extrae la información para guardarla
     * en la lista.
     * @param archivo Archivo donde se encuentra la lista
     * @return La lista importada del archivo
     */
    public List<Alumno> cargarAlumnos(File archivo);

    /**
     * Desde un archivo CSV se extrae la información para guardarla
     * en la lista.
     * @param archivo Archivo donde se encuentra la lista
     * @return La lista importada del archivo
     */
    public List<Alumno> cargarAlumnosCSV(File archivo);

    /**
     * Crea un archivo TXT y guarda en él la lista de alumnos
     * pasada por parámetro
     * @param listaAlumnos Lista de alumnos a guardar en el archivo
     * @param archivo Archivo donde se guarda la lista
     */
    public void guardarAlumnosTXT (List<Alumno> listaAlumnos, File archivo);
}

