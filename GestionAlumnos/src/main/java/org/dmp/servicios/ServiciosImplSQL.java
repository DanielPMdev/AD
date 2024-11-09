package org.dmp.servicios;

import org.dmp.modelo.Alumno;
import org.dmp.modelo.Hobby;
import org.dmp.repositorio.AlumnoDAO;
import org.dmp.repositorio.AlumnoDAOImpl;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danielpm.dev
 */
public class ServiciosImplSQL implements Servicios {
    List<Alumno> listaAlumnos;
    AlumnoDAO alumnoDAO;

    public ServiciosImplSQL() {
        alumnoDAO = new AlumnoDAOImpl();
    }

    public ServiciosImplSQL(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
        this.alumnoDAO = new AlumnoDAOImpl();
    }

    @Override
    public boolean añadirAlumno(Alumno a) {
        return alumnoDAO.insertarAlumno(a);
    }

    @Override
    public boolean borrarAlumno(Alumno a) {
        return alumnoDAO.eliminarAlumno(a.getNombre(), a.getApellidos());
    }

    @Override
    public boolean actualizarAlumno(Alumno a) {
        return alumnoDAO.actualizarAlumno(a);
    }

    @Override
    public List<Alumno> filtrarAlumnos(String criterio, String valor) {
        return alumnoDAO.consultarPorAtributo(criterio, valor);
    }


    @Override
    public List<Alumno> buscarAlumnoApellidos(String apellidos) {
        return alumnoDAO.consultarPorApellidos(apellidos);
    }


    //SIN IMPLEMENTAR

    @Override
    public void guardarAlumnos(List<Alumno> listaAlumnos, File archivo) {

    }

    @Override
    public void guardarAlumnosCSV(List<Alumno> listaAlumnos, File archivo) {

    }

    @Override
    public void guardarAlumnosTXT(List<Alumno> listaAlumnos, File archivo) {

    }

    @Override
    public List<Alumno> cargarAlumnos(File archivo) {
        return List.of();
    }

    @Override
    public List<Alumno> cargarAlumnosCSV(File archivo) {
        return List.of();
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
}


/*
List<Alumno> alumnosEncontrados = new ArrayList<>();

        if (alumnoDAO.consultarPorAtributo(criterio, valor)) {
            try (PreparedStatement statement = alumnoDAO.getStatement()) {
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
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return alumnosEncontrados;
 */