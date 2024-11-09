package org.dmp.modelo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * La clase {@link Alumno} representa un alumno dentro de la aplicación.
 * Implementa la interfaz {@link Serializable}, permitiendo que los
 * objetos de esta clase sean serializados para almacenamiento o
 * transmisión.
 *
 * <p>Esta clase contiene información relevante sobre el alumno,
 * incluyendo datos personales, de contacto, educación y actividades
 * extracurriculares, a través de un listado de hobbies.</p>
 */
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L; // Identificador de versión para la serialización

    private String nombre;
    private String apellidos;
    private String mail;
    private String telefono;
    private String localidad;
    private String estudios;
    @JacksonXmlElementWrapper(useWrapping = false)
    private LocalDate fechaNacimiento;
    private String ciclo;
    private int nivelOrdenador;
    private String carnet;
    private String motivacion;
    @JacksonXmlElementWrapper(useWrapping = false)
    private ArrayList<Hobby> hobbies;

    public Alumno() {
    }

    /**
     * Constructor de la clase {@link Alumno}.
     *
     * Este constructor inicializa un nuevo objeto {@link Alumno} con los
     * atributos proporcionados.
     *
     * @param nombre El nombre del alumno.
     * @param apellidos Los apellidos del alumno.
     * @param mail La dirección de correo electrónico del alumno.
     * @param telefono El número de teléfono del alumno.
     * @param localidad La localidad del alumno.
     * @param estudios El nivel de estudios del alumno.
     * @param fechaNacimiento La fecha de nacimiento del alumno.
     * @param ciclo El ciclo académico del alumno.
     * @param nivelOrdenador El nivel de conocimiento en informática del alumno.
     * @param carnet El estado del carnet del alumno.
     * @param motivacion La motivación del alumno para estudiar.
     * @param hobbies La lista de hobbies del alumno.
     */
    public Alumno(String nombre, String apellidos, String mail, String telefono, String localidad, String estudios, LocalDate fechaNacimiento, String ciclo, int nivelOrdenador, String carnet, String motivacion, ArrayList<Hobby> hobbies) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
        this.telefono = telefono;
        this.localidad = localidad;
        this.estudios = estudios;
        this.fechaNacimiento = fechaNacimiento;
        this.ciclo = ciclo;
        this.nivelOrdenador = nivelOrdenador;
        this.carnet = carnet;
        this.motivacion = motivacion;
        this.hobbies = hobbies;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public int getNivelOrdenador() {
        return nivelOrdenador;
    }

    public void setNivelOrdenador(int nivelOrdenador) {
        this.nivelOrdenador = nivelOrdenador;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    public ArrayList<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return nombre + ", " + apellidos + ", " + mail + ", " + telefono + ", " + localidad +
                ", " + estudios + ", " + fechaNacimiento + ", " + ciclo + ", " + nivelOrdenador +
                ", " + carnet + ", " + motivacion + ", " + hobbiesToString();
    }

    // Método para convertir la lista de hobbies a una cadena
    private String hobbiesToString() {
        StringBuilder hobbiesStr = new StringBuilder();
        for (Hobby hobby : hobbies) {
            hobbiesStr.append(hobby.name()).append(", ");
        }
        // Elimina la última coma y espacio si hay hobbies
        if (!hobbiesStr.isEmpty()) {
            hobbiesStr.setLength(hobbiesStr.length() - 2);
        }
        return hobbiesStr.toString();
    }
}
