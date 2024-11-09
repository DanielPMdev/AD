package org.dmp.modelo;

import org.dmp.servicios.Servicios;
import org.dmp.servicios.ServiciosImpl;
import org.dmp.servicios.ServiciosImplSQL;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@link ModeloAlumno} sirve como estructura de almacenamiento
 * para gestionar una colección de objetos {@link Alumno}. Esta clase
 * permite organizar y manipular los datos de los alumnos dentro de
 * la aplicación.
 *
 * <p>La clase proporciona métodos para añadir, eliminar y acceder
 * a los alumnos, facilitando la gestión de la información educativa.</p>
 */
public class ModeloAlumno {

    private List<Alumno> listaAlumnos;
    private Servicios servicios;

    public ModeloAlumno(Servicios servicios) {
        this.listaAlumnos = new ArrayList<>();

        // Asegúrate de que el servicio tenga la lista de alumnos si es necesario
        if (servicios instanceof ServiciosImpl) {
            ((ServiciosImpl) servicios).setListaAlumnos(listaAlumnos);
        } else if (servicios instanceof ServiciosImplSQL) {
            ((ServiciosImplSQL) servicios).setListaAlumnos(listaAlumnos);
        }

        this.servicios = servicios;
    }

    /**
     * Constructor de la clase {@link ModeloAlumno}.
     *
     * Este constructor inicializa la lista de alumnos como una nueva lista vacía
     * y crea una instancia de {@link ServiciosImpl}, proporcionando los servicios
     * necesarios para gestionar la lista de alumnos.
     */
    public ModeloAlumno() {
        listaAlumnos = new ArrayList<Alumno>();
        this.servicios = new ServiciosImpl(listaAlumnos);
    }

    public Servicios getServicios() {
        return servicios;
    }

    public void setServicios(Servicios servicios) {
        this.servicios = servicios;
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;

        // Realizar un cast a ServiciosImpl para llamar al método
        if (this.servicios instanceof ServiciosImpl) {
            ((ServiciosImpl) this.servicios).setListaAlumnos(this.listaAlumnos);
        }
    }
}
