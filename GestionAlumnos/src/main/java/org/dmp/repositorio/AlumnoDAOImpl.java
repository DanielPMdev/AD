package org.dmp.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.dmp.modelo.Alumno;
import org.dmp.modelo.Hobby;
import org.dmp.util.ConexionOra;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author danielpm.dev
 */
public class AlumnoDAOImpl implements AlumnoDAO {

    private Connection connection;
    List<Alumno> listaAlumnos;

    public AlumnoDAOImpl() {
    }

    public AlumnoDAOImpl(Connection connection, List<Alumno> lista) {
        this.connection = connection;
        this.listaAlumnos = lista;
    }

    @Override
    public boolean insertarAlumno(Alumno alumno) {
        String sql = "INSERT INTO Alumno (nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Si la conexión no ha sido inicializada, inicialízala
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer los parámetros del PreparedStatement usando los atributos del objeto Alumno
                statement.setString(1, alumno.getNombre());
                statement.setString(2, alumno.getApellidos());
                statement.setString(3, alumno.getMail());
                statement.setString(4, alumno.getTelefono());
                statement.setString(5, alumno.getLocalidad());
                statement.setString(6, alumno.getEstudios());
                statement.setDate(7, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
                statement.setString(8, alumno.getCiclo());
                statement.setInt(9, alumno.getNivelOrdenador());
                statement.setString(10, alumno.getCarnet());
                statement.setString(11, alumno.getMotivacion());

                // Convertir la lista de hobbies a una cadena separada por comas
                if (alumno.getHobbies() != null && !alumno.getHobbies().isEmpty()) {
                    String hobbiesStr = String.join(",", alumno.getHobbies().stream()
                            .map(Hobby::name)
                            .toArray(String[]::new));
                    statement.setString(12, hobbiesStr);
                } else {
                    statement.setString(12, null);
                }

                // Ejecutar la inserción y devolver el número de filas afectadas
                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0; // Verificar si se insertó al menos una fila
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public boolean actualizarAlumno(Alumno alumno) {
        String sql = "UPDATE Alumno SET nombre = ?, apellidos = ?, mail = ?, telefono = ?, localidad = ?, "
                + "estudios = ?, fechaNacimiento = ?, ciclo = ?, nivelOrdenador = ?, carnet = ?, "
                + "motivacion = ?, hobbies = ? WHERE nombre = ? AND apellidos = ?";

        try {
            // Si la conexión no ha sido inicializada, inicialízala
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer los parámetros del PreparedStatement usando los atributos del objeto Alumno
                statement.setString(1, alumno.getNombre());
                statement.setString(2, alumno.getApellidos());
                statement.setString(3, alumno.getMail());
                statement.setString(4, alumno.getTelefono());
                statement.setString(5, alumno.getLocalidad());
                statement.setString(6, alumno.getEstudios());
                statement.setDate(7, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
                statement.setString(8, alumno.getCiclo());
                statement.setInt(9, alumno.getNivelOrdenador());
                statement.setString(10, alumno.getCarnet());
                statement.setString(11, alumno.getMotivacion());

                // Convertir la lista de hobbies a una cadena separada por comas
                if (alumno.getHobbies() != null && !alumno.getHobbies().isEmpty()) {
                    String hobbiesStr = String.join(",", alumno.getHobbies().stream()
                            .map(Hobby::name)
                            .toArray(String[]::new));
                    statement.setString(12, hobbiesStr);
                } else {
                    statement.setString(12, null);
                }

                //Establecer los parámetros para la condición WHERE
                statement.setString(13, alumno.getNombre());
                statement.setString(14, alumno.getApellidos());

                // Ejecutar la actualización y devolver el número de filas afectadas
                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0; // Verificar si se insertó al menos una fila
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public boolean eliminarAlumno(String nombreAlumno, String apellidosAlumno) {
        String sql = "DELETE FROM Alumno WHERE nombre = ? AND apellidos = ?";

        try {
            // Si la conexión no ha sido inicializada, inicialízala
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer el parámetro del PreparedStatement
                statement.setString(1, nombreAlumno);
                statement.setString(2, apellidosAlumno);

                // Ejecutar la eliminación y devolver el número de filas afectadas
                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0; // Verificar si se insertó al menos una fila

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public List<Alumno> consultarPorApellidos(String apellidosAlumno) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();

        String sql = "SELECT * FROM Alumno WHERE apellidos LIKE ?";

        try {
            // Si la conexión no ha sido inicializada, inicialízala
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer el parámetro del PreparedStatement
                statement.setString(1, "%" + apellidosAlumno + "%"); //AÑADIR LOS % AL SER UN LIKE

                // Ejecutar la busqueda y devolver el resultSet en la variable booleana
                statement.execute();
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        Alumno alumno = new Alumno();
                        alumno.setNombre(resultSet.getString("nombre"));
                        alumno.setApellidos(resultSet.getString("apellidos"));
                        alumno.setMail(resultSet.getString("mail"));
                        alumno.setTelefono(resultSet.getString("telefono"));
                        alumno.setLocalidad(resultSet.getString("localidad"));
                        alumno.setEstudios(resultSet.getString("estudios"));
                        alumno.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
                        alumno.setCiclo(resultSet.getString("ciclo"));
                        alumno.setNivelOrdenador(resultSet.getInt("nivelOrdenador"));
                        alumno.setCarnet(resultSet.getString("carnet"));
                        alumno.setMotivacion(resultSet.getString("motivacion"));

                        // Convertir hobbies a ArrayList<Hobby>
                        String hobbiesStr = resultSet.getString("hobbies");
                        if (hobbiesStr != null && !hobbiesStr.isEmpty()) {
                            ArrayList<Hobby> hobbies = new ArrayList<>();
                            for (String hobby : hobbiesStr.split(",")) {
                                hobbies.add(Hobby.valueOf(hobby.trim()));
                            }
                            alumno.setHobbies(hobbies);
                        }

                        alumnosEncontrados.add(alumno);
                    }
                }

                return alumnosEncontrados;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return alumnosEncontrados;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public List<Alumno> consultarPorAtributo(String atributo, String valor) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();

        // Validar el atributo
        if (!List.of("Nombre", "Localidad", "Estudios", "Carnet").contains(atributo)) {
            //System.out.println("Atributo no válido: " + atributo);
            return alumnosEncontrados;
        }

        atributo = atributo.toLowerCase();

        // Construir la consulta SQL con el nombre de atributo directamente en la cadena
        String sql = "SELECT * FROM Alumno WHERE " + atributo + " = ?";

        try {
            // Si la conexión no ha sido inicializada, inicialízala
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer el valor del parámetro en la consulta
                statement.setString(1, valor);

                statement.execute();
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        Alumno alumno = new Alumno();
                        alumno.setNombre(resultSet.getString("nombre"));
                        alumno.setApellidos(resultSet.getString("apellidos"));
                        alumno.setMail(resultSet.getString("mail"));
                        alumno.setTelefono(resultSet.getString("telefono"));
                        alumno.setLocalidad(resultSet.getString("localidad"));
                        alumno.setEstudios(resultSet.getString("estudios"));
                        alumno.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
                        alumno.setCiclo(resultSet.getString("ciclo"));
                        alumno.setNivelOrdenador(resultSet.getInt("nivelOrdenador"));
                        alumno.setCarnet(resultSet.getString("carnet"));
                        alumno.setMotivacion(resultSet.getString("motivacion"));

                        // Convertir hobbies a ArrayList<Hobby>
                        String hobbiesStr = resultSet.getString("hobbies");
                        if (hobbiesStr != null && !hobbiesStr.isEmpty()) {
                            ArrayList<Hobby> hobbies = new ArrayList<>();
                            for (String hobby : hobbiesStr.split(",")) {
                                hobbies.add(Hobby.valueOf(hobby.trim()));
                            }
                            alumno.setHobbies(hobbies);
                        }

                        alumnosEncontrados.add(alumno);
                    }
                }
            }
            return alumnosEncontrados;
        } catch (SQLException e) {
            e.printStackTrace();
            return alumnosEncontrados;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public List<Alumno> importarDatos(File archivo, String formato) {
        List<Alumno> listaAlumnosImportados = new ArrayList<Alumno>();

        // Inicializar el ObjectMapper adecuado según el formato
        ObjectMapper mapper;
        try {
            if ("JSON".equalsIgnoreCase(formato)) {
                mapper = new ObjectMapper();
            } else if ("XML".equalsIgnoreCase(formato)) {
                mapper = new XmlMapper();
            } else {
                System.out.println("Formato no soportado: " + formato);
                return listaAlumnosImportados;
            }

            // Leer el archivo en una lista de objetos Alumno
            listaAlumnosImportados = List.of(mapper.readValue(archivo, Alumno[].class));

            // Conectar a la base de datos Oracle
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            // Preparar la sentencia SQL para la inserción
            String sql = "INSERT INTO Alumno (nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Recorrer los objetos Alumno y realizar inserciones
                for (Alumno alumno : listaAlumnosImportados) {
                    statement.setString(1, alumno.getNombre());
                    statement.setString(2, alumno.getApellidos());
                    statement.setString(3, alumno.getMail());
                    statement.setString(4, alumno.getTelefono());
                    statement.setString(5, alumno.getLocalidad());
                    statement.setString(6, alumno.getEstudios());
                    statement.setDate(7, Date.valueOf(alumno.getFechaNacimiento())); // Convertir LocalDate a java.sql.Date
                    statement.setString(8, alumno.getCiclo());
                    statement.setInt(9, alumno.getNivelOrdenador());
                    statement.setString(10, alumno.getCarnet());
                    statement.setString(11, alumno.getMotivacion());

                    // Convertir ArrayList<Hobby> a String (e.g., "LECTURA,DEPORTE,MUSICA")
                    String hobbies = alumno.getHobbies().stream()
                            .map(Hobby::name)
                            .collect(Collectors.joining(","));
                    statement.setString(12, hobbies);

                    // Ejecutar la inserción
                    statement.executeUpdate();
                }
            }
            System.out.println("Datos importados correctamente desde " + formato);
            return listaAlumnosImportados;
        } catch (Exception e) {
            e.printStackTrace();
            return listaAlumnosImportados;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public boolean exportarDatos(List<Alumno> listaAlumnos, File archivo, String formato) {
        // Inicializar la lista de alumnos
        listaAlumnos = new ArrayList<>();

        try {
            // Conectar a la base de datos Oracle
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            // Consultar los datos de la tabla Alumno
            String sql = "SELECT nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies FROM Alumno";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Recorrer el ResultSet y añadir cada registro a la lista de alumnos
                while (resultSet.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setNombre(resultSet.getString("nombre"));
                    alumno.setApellidos(resultSet.getString("apellidos"));
                    alumno.setMail(resultSet.getString("mail"));
                    alumno.setTelefono(resultSet.getString("telefono"));
                    alumno.setLocalidad(resultSet.getString("localidad"));
                    alumno.setEstudios(resultSet.getString("estudios"));
                    alumno.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
                    alumno.setCiclo(resultSet.getString("ciclo"));
                    alumno.setNivelOrdenador(resultSet.getInt("nivelOrdenador"));
                    alumno.setCarnet(resultSet.getString("carnet"));
                    alumno.setMotivacion(resultSet.getString("motivacion"));

                    // Convertir hobbies a ArrayList<Hobby>
                    String hobbiesStr = resultSet.getString("hobbies");
                    if (hobbiesStr != null && !hobbiesStr.isEmpty()) {
                        ArrayList<Hobby> hobbies = new ArrayList<>();
                        for (String hobby : hobbiesStr.split(",")) {
                            hobbies.add(Hobby.valueOf(hobby.trim()));
                        }
                        alumno.setHobbies(hobbies);
                    }

                    listaAlumnos.add(alumno);
                }
            }

            // Inicializar el ObjectMapper adecuado según el formato
            ObjectMapper mapper;
            if ("JSON".equalsIgnoreCase(formato)) {
                mapper = new ObjectMapper();
            } else if ("XML".equalsIgnoreCase(formato)) {
                mapper = new XmlMapper();
            } else {
                System.out.println("Formato no soportado: " + formato);
                return false;
            }

            // Escribir los datos al archivo especificado
            mapper.writeValue(archivo, listaAlumnos);
            System.out.println("Datos exportados correctamente a " + archivo + " en formato " + formato);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public List<Alumno> importarDatosDesdeJSON(File archivo) {
        List<Alumno> listaAlumnosImportados = new ArrayList<>();

        try {

            // Inicializar ObjectMapper para JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            // Leer el archivo JSON en una lista de objetos Alumno
            listaAlumnosImportados = List.of(mapper.readValue(archivo, Alumno[].class));

            // Conectar a la base de datos Oracle
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            // Preparar la sentencia SQL para la inserción
            String sql = "INSERT INTO Alumno (nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Recorrer los objetos Alumno y realizar inserciones
                for (Alumno alumno : listaAlumnosImportados) {
                    statement.setString(1, alumno.getNombre());
                    statement.setString(2, alumno.getApellidos());
                    statement.setString(3, alumno.getMail());
                    statement.setString(4, alumno.getTelefono());
                    statement.setString(5, alumno.getLocalidad());
                    statement.setString(6, alumno.getEstudios());
                    statement.setDate(7, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
                    statement.setString(8, alumno.getCiclo());
                    statement.setInt(9, alumno.getNivelOrdenador());
                    statement.setString(10, alumno.getCarnet());
                    statement.setString(11, alumno.getMotivacion());

                    // Convertir ArrayList<Hobby> a String
                    String hobbies = alumno.getHobbies().stream()
                            .map(Hobby::name)
                            .collect(Collectors.joining(","));
                    statement.setString(12, hobbies);

                    // Ejecutar la inserción
                    statement.executeUpdate();
                }
            }
            System.out.println("Datos importados correctamente desde JSON");
            return listaAlumnosImportados;
        } catch (Exception e) {
            e.printStackTrace();
            return listaAlumnosImportados;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public boolean exportarDatosAJSON(List<Alumno> listaAlumnos, File archivo) {

        try {
            // Conectar a la base de datos Oracle
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            // Consultar los datos de la tabla Alumno
            String sql = "SELECT nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies FROM Alumno";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Recorrer el ResultSet y añadir cada registro a la lista de alumnos
                while (resultSet.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setNombre(resultSet.getString("nombre"));
                    alumno.setApellidos(resultSet.getString("apellidos"));
                    alumno.setMail(resultSet.getString("mail"));
                    alumno.setTelefono(resultSet.getString("telefono"));
                    alumno.setLocalidad(resultSet.getString("localidad"));
                    alumno.setEstudios(resultSet.getString("estudios"));
                    alumno.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
                    alumno.setCiclo(resultSet.getString("ciclo"));
                    alumno.setNivelOrdenador(resultSet.getInt("nivelOrdenador"));
                    alumno.setCarnet(resultSet.getString("carnet"));
                    alumno.setMotivacion(resultSet.getString("motivacion"));

                    // Convertir hobbies a ArrayList<Hobby>
                    String hobbiesStr = resultSet.getString("hobbies");
                    if (hobbiesStr != null && !hobbiesStr.isEmpty()) {
                        ArrayList<Hobby> hobbies = new ArrayList<>();
                        for (String hobby : hobbiesStr.split(",")) {
                            hobbies.add(Hobby.valueOf(hobby.trim()));
                        }
                        alumno.setHobbies(hobbies);
                    }

                    listaAlumnos.add(alumno);
                }
            }

            // Escribir los datos al archivo JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(archivo, listaAlumnos);
            System.out.println("Datos exportados correctamente a " + archivo + " en formato JSON");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }


    @Override
    public List<Alumno> importarDatosDesdeXML(File archivo) {
        List<Alumno> listaAlumnosImportados = new ArrayList<>();

        try {
            // Inicializar XmlMapper para XML
            XmlMapper mapper = new XmlMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            // Leer el archivo XML en una lista de objetos Alumno
            listaAlumnosImportados = List.of(mapper.readValue(archivo, Alumno[].class));

            // Conectar a la base de datos Oracle
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            // Preparar la sentencia SQL para la inserción
            String sql = "INSERT INTO Alumno (nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Recorrer los objetos Alumno y realizar inserciones
                for (Alumno alumno : listaAlumnosImportados) {
                    statement.setString(1, alumno.getNombre());
                    statement.setString(2, alumno.getApellidos());
                    statement.setString(3, alumno.getMail());
                    statement.setString(4, alumno.getTelefono());
                    statement.setString(5, alumno.getLocalidad());
                    statement.setString(6, alumno.getEstudios());
                    statement.setDate(7, Date.valueOf(alumno.getFechaNacimiento()));
                    statement.setString(8, alumno.getCiclo());
                    statement.setInt(9, alumno.getNivelOrdenador());
                    statement.setString(10, alumno.getCarnet());
                    statement.setString(11, alumno.getMotivacion());

                    // Convertir ArrayList<Hobby> a String
                    String hobbies = alumno.getHobbies().stream()
                            .map(Hobby::name)
                            .collect(Collectors.joining(","));
                    statement.setString(12, hobbies);

                    // Ejecutar la inserción
                    statement.executeUpdate();
                }
            }
            System.out.println("Datos importados correctamente desde XML");
            return listaAlumnosImportados;
        } catch (Exception e) {
            e.printStackTrace();
            return listaAlumnosImportados;
        } finally {
            cerrarConexion();
        }
    }


    @Override
    public boolean exportarDatosAXML(List<Alumno> listaAlumnos, File archivo) {
        try {
            // Conectar a la base de datos Oracle
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            // Consultar los datos de la tabla Alumno
            String sql = "SELECT nombre, apellidos, mail, telefono, localidad, estudios, fechaNacimiento, ciclo, nivelOrdenador, carnet, motivacion, hobbies FROM Alumno";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Recorrer el ResultSet y añadir cada registro a la lista de alumnos
                while (resultSet.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setNombre(resultSet.getString("nombre"));
                    alumno.setApellidos(resultSet.getString("apellidos"));
                    alumno.setMail(resultSet.getString("mail"));
                    alumno.setTelefono(resultSet.getString("telefono"));
                    alumno.setLocalidad(resultSet.getString("localidad"));
                    alumno.setEstudios(resultSet.getString("estudios"));
                    alumno.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
                    alumno.setCiclo(resultSet.getString("ciclo"));
                    alumno.setNivelOrdenador(resultSet.getInt("nivelOrdenador"));
                    alumno.setCarnet(resultSet.getString("carnet"));
                    alumno.setMotivacion(resultSet.getString("motivacion"));

                    // Convertir hobbies a ArrayList<Hobby>
                    String hobbiesStr = resultSet.getString("hobbies");
                    if (hobbiesStr != null && !hobbiesStr.isEmpty()) {
                        ArrayList<Hobby> hobbies = new ArrayList<>();
                        for (String hobby : hobbiesStr.split(",")) {
                            hobbies.add(Hobby.valueOf(hobby.trim()));
                        }
                        alumno.setHobbies(hobbies);
                    }

                    listaAlumnos.add(alumno);
                }
            }

            // Escribir los datos al archivo XML
            XmlMapper mapper = new XmlMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(archivo, listaAlumnos);
            System.out.println("Datos exportados correctamente a " + archivo + " en formato XML");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public List<Alumno> mostrarAlumnos() {
        List<Alumno> listaAlumnosImportados = new ArrayList<>();

        try {
            // Conectar a la base de datos Oracle
            if (connection == null || connection.isClosed()) {
                connection = ConexionOra.getConexion();
            }

            // Preparar la sentencia SQL para la consulta
            String sql = "SELECT * FROM Alumno";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Recorrer los resultados y mapearlos a objetos Alumno
                while (resultSet.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setNombre(resultSet.getString("nombre"));
                    alumno.setApellidos(resultSet.getString("apellidos"));
                    alumno.setMail(resultSet.getString("mail"));
                    alumno.setTelefono(resultSet.getString("telefono"));
                    alumno.setLocalidad(resultSet.getString("localidad"));
                    alumno.setEstudios(resultSet.getString("estudios"));
                    alumno.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
                    alumno.setCiclo(resultSet.getString("ciclo"));
                    alumno.setNivelOrdenador(resultSet.getInt("nivelOrdenador"));
                    alumno.setCarnet(resultSet.getString("carnet"));
                    alumno.setMotivacion(resultSet.getString("motivacion"));

                    // Convertir hobbies a ArrayList<Hobby>
                    String hobbiesStr = resultSet.getString("hobbies");
                    if (hobbiesStr != null && !hobbiesStr.isEmpty()) {
                        ArrayList<Hobby> hobbies = new ArrayList<>();
                        for (String hobby : hobbiesStr.split(",")) {
                            hobbies.add(Hobby.valueOf(hobby.trim()));
                        }
                        alumno.setHobbies(hobbies);
                    }

                    // Añadir el alumno a la lista
                    listaAlumnosImportados.add(alumno);
                }
            }

            System.out.println("Datos obtenidos correctamente de la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return listaAlumnosImportados;
    }



    @Override
    public boolean ejecutarScript(String archivo) {
        return false;
    }

    @Override
    public List<String> obtenerHistorial() {
        return List.of();
    }

    @Override
    public boolean guardarHistorial(String archivo) {
        return false;
    }

    @Override
    public boolean cargarHistorial(String archivo) {
        return false;
    }

    @Override
    public boolean conectarOracle() {
        return false;
    }

    @Override
    public boolean conectarSQLite() {
        return false;
    }

    private void cerrarConexion() {
        // Cerrar la conexión si está abierta
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Evitar referencias a una conexión cerrada
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
