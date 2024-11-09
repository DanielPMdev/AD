package org.dmp.servicios;

import org.dmp.modelo.Alumno;
import org.dmp.modelo.Hobby;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.dmp.controlador.Controlador.mostrarHobbiesCSV;

/**
 * La clase {@link ServiciosImpl} implementa la interfaz {@link Servicios}
 * y proporciona la lógica para gestionar la lista de alumnos. Esta clase
 * ofrece métodos para realizar diversas operaciones sobre los datos de
 * los alumnos, como agregar, eliminar, actualizar y cargar información.
 *
 * <p>Es responsable de la manipulación de la lista de alumnos y de la
 * interacción con los datos, facilitando la lógica de negocio necesaria
 * para la gestión de la aplicación.</p>
 */
public class ServiciosImpl implements Servicios{

    List<Alumno> listaAlumnos;

    public ServiciosImpl() {
        listaAlumnos = new ArrayList<>();
    }

    public ServiciosImpl (List<Alumno> lista) {
        this.listaAlumnos = lista;
    }

    @Override
    public boolean añadirAlumno(Alumno a) {
        // Verificar si el contacto es nulo antes de hacer cualquier operación
        if (a == null) {
            return false;
        }

        // Buscar la posición por apellidos para evitar duplicados
        int posicion = this.posicionAlumno(a.getNombre(), a.getApellidos());
        if (posicion == -1) { // Si NO lo encuentra, lo añade
            listaAlumnos.add(a);
            return true;
        }

        // Si ya existe un contacto con los mismos apellidos, no lo añade
        return false;
    }

    @Override
    public boolean borrarAlumno(Alumno a) {
        // Verificar si el contacto es nulo antes de hacer cualquier operación
        if (a == null) {
            return false;
        }

        // Buscar la posición por apellidos para evitar duplicados
        int posicion = this.posicionAlumno(a.getNombre(), a.getApellidos());
        if (posicion != -1) { // Si lo encuentra lo borra
            listaAlumnos.remove(posicion);
            return true;
        }

        // Si NO existe un contacto con los mismos apellidos, no lo borra
        return false;
    }

    @Override
    public boolean actualizarAlumno(Alumno a) {
        // Verificar si el contacto es nulo antes de hacer cualquier operación
        if (a == null) {
            return false;
        }

        int posicion = this.posicionAlumno(a.getNombre(), a.getApellidos());
        if (posicion != -1){ //Si lo encuentra lo actualiza
            Alumno alumno = listaAlumnos.get(posicion); //Uso este alumno para cambiarle las propiedades
            alumno.setNombre(a.getNombre()); //Le asigno los valores del Alumno a
            alumno.setApellidos(a.getApellidos());
            alumno.setMail(a.getMail());
            alumno.setTelefono(a.getTelefono());
            alumno.setLocalidad(a.getLocalidad());
            alumno.setEstudios(a.getEstudios());
            alumno.setFechaNacimiento(a.getFechaNacimiento());
            alumno.setCiclo(a.getCiclo());
            alumno.setNivelOrdenador(a.getNivelOrdenador());
            alumno.setCarnet(a.getCarnet());
            alumno.setMotivacion(a.getMotivacion());
            alumno.setHobbies(a.getHobbies());
            return true;
        }
        return false;
    }

    @Override
    public List<Alumno> buscarAlumnoApellidos(String apellidos) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();

        for (Alumno alumno : listaAlumnos) {
            if (alumno.getApellidos().toLowerCase().contains(apellidos.toLowerCase())) {
                alumnosEncontrados.add(alumno);
            }
        }

        return alumnosEncontrados;
    }

    @Override
    public List<Alumno> filtrarAlumnos(String criterio, String valor) {
        return listaAlumnos.stream()
                .filter(alumno -> {
                    boolean matches = switch (criterio) {
                        // Devolvemos los alumnos que son iguales sin importar mayúsculas/minúsculas
                        case "Nombre" -> alumno.getNombre().equalsIgnoreCase(valor);
                        case "Localidad" -> alumno.getLocalidad().equalsIgnoreCase(valor);
                        case "Estudios" -> alumno.getEstudios().equalsIgnoreCase(valor);
                        case "Carnet" -> alumno.getCarnet().equalsIgnoreCase(valor);
                        default -> false;
                    };
                    // Imprimir el resultado del filtro para depuración
                    System.out.printf("Filtrando: %s, Criterio: %s, Valor: %s, Coincide: %s%n",
                            alumno.getNombre(), criterio, valor, matches);
                    return matches;
                })
                .collect(Collectors.toList());
    }


    @Override
    public void guardarAlumnos(List<Alumno> listaAlumnos, File archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(listaAlumnos);  // Escribe la lista de contactos en el archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Alumno> cargarAlumnos(File archivo) {
        List<Alumno> listaAlumnos = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            listaAlumnos = (List<Alumno>) ois.readObject();  // Lee la lista de listaAlumnos desde el archivo
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Clase no encontrada: " + e.getMessage());
        }

        return listaAlumnos;
    }

    @Override
    public void guardarAlumnosCSV(List<Alumno> listaAlumnos, File archivo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            // Escribir el encabezado del CSV
            bw.write("Nombre;Apellidos;email;Teléfono;Localidad;Estudios;Fecha Nacimiento;Ciclo;Nivel Ordenador;Carnet de conducir;Motivación;Hobbies");
            bw.newLine();

            // Escribir los datos de cada alumno
            for (Alumno alumno : listaAlumnos) {
                // Convertir la lista de hobbies a una cadena separada por punto y coma
                String hobbies = alumno.getHobbies().stream()
                        .map(Hobby::name)
                        .collect(Collectors.joining(";"));

                bw.write(String.format("\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%d\";\"%s\";\"%s\";\"%s\"",
                        alumno.getNombre(),
                        alumno.getApellidos(),
                        alumno.getMail(),
                        alumno.getTelefono(),
                        alumno.getLocalidad(),
                        alumno.getEstudios(),
                        alumno.getFechaNacimiento().format(formatter),
                        alumno.getCiclo(),
                        alumno.getNivelOrdenador(),
                        alumno.getCarnet(),
                        alumno.getMotivacion(),
                        hobbies));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Alumno> cargarAlumnosCSV(File archivo) {
        List<Alumno> listaAlumnos = new ArrayList<>();
        String linea;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine(); // Saltar la primera línea de encabezados
            while ((linea = br.readLine()) != null) {
                // Cambiamos el delimitador a punto y coma
                String[] datos = linea.split(",", -1);

                // Ignorar la marca temporal (columna 0)
                String nombre = datos[1].trim();
                String apellidos = datos[2].trim();
                String mail = datos[3].trim();
                String telefono = datos[4].trim();
                String localidad = datos[5].trim();
                String estudios = datos[6].trim();
                LocalDate fechaNacimiento = LocalDate.parse(datos[7].trim(), formatter);
                String ciclo = datos[8].trim();
                int nivelOrdenador = Integer.parseInt(datos[9].trim());
                String carnet = datos[10].trim();
                String motivacion = datos[11].trim();

                // Procesamos la cadena de hobbies separada por punto y coma
                String cadenaHobbies = datos[12].trim();
                ArrayList<Hobby> hobbies = mostrarHobbiesCSV(cadenaHobbies);

                // Creamos el objeto Alumno y lo añadimos a la lista
                Alumno a = new Alumno(nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies);
                listaAlumnos.add(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaAlumnos;
    }

    @Override
    public void guardarAlumnosTXT(List<Alumno> listaAlumnos, File archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Alumno alumno : listaAlumnos) {
                bw.write(alumno.toString());
                bw.newLine();  // Para separar cada alumno en una línea nueva
            }
            System.out.println("Lista de alumnos guardada correctamente en el fichero.");
        } catch (IOException e) {
            System.out.println("Error al guardar la lista de alumnos: " + e.getMessage());
        }
    }

    /**
     * Devuelve la posición de un contacto en la lista.
     * Si no lo encuentra, devuelve -1.
     * @param nombre Nombre del contacto a buscar.
     * @param apellidos Apellidos del contacto a buscar.
     * @return La posición del contacto o -1 si no lo encuentra.
     */
    private int posicionAlumno(String nombre, String apellidos) {
        // Verificar si la lista está vacía o no inicializada
        if (listaAlumnos == null || listaAlumnos.isEmpty()) {
            return -1;
        }

        // Recorremos la lista buscando el contacto por su nombre y apellidos
        for (int i = 0; i < listaAlumnos.size(); i++) {
            Alumno contacto = listaAlumnos.get(i);
            // Comparamos nombre y apellidos
            if (contacto.getNombre().equals(nombre) && contacto.getApellidos().equals(apellidos)) {
                return i; // Devuelve el índice si el nombre y los apellidos coinciden
            }
        }

        return -1; // No se encontró el contacto
    }

    /**
     * Devuelve la posición de un contacto en la lista.
     * Según su apellido con el método contains
     * Si no lo encuentra, devuelve -1.
     * @param apellidos Apellidos del contacto a buscar.
     * @return La posición del contacto o -1 si no lo encuentra.
     */
    private int posicionAlumno(String apellidos) {
        // Verificar si la lista está vacía o no inicializada
        if (listaAlumnos == null || listaAlumnos.isEmpty()) {
            return -1;
        }

        // Recorremos la lista buscando el contacto por su nombre y apellidos
        for (int i = 0; i < listaAlumnos.size(); i++) {
            Alumno contacto = listaAlumnos.get(i);
            // Comparamos nombre y apellidos
            if (contacto.getApellidos().contains(apellidos)) {
                return i; // Devuelve el índice si el nombre y los apellidos coinciden
            }
        }

        return -1; // No se encontró el contacto
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
}
