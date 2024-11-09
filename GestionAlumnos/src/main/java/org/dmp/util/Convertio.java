package org.dmp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.dmp.modelo.Alumno;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Convertio {

    public static void guardarAlumnosEnJson(List<Alumno> listaAlumnos, File archivo) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Escribimos la lista de alumnos en el archivo JSON
            mapper.writeValue(archivo, listaAlumnos);
            System.out.println("Alumnos guardados en el fichero JSON con éxito.");
        } catch (IOException e) {
            System.err.println("Error al guardar los alumnos en el fichero JSON: " + e.getMessage());
        }
    }

    public static List<Alumno> cargarAlumnosDesdeJson(File archivo) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Registrar el módulo para LocalDate
        List<Alumno> listaAlumnos = null;

        try {
            // Cargamos la lista de alumnos desde el archivo JSON
            listaAlumnos = mapper.readValue(archivo, new TypeReference<List<Alumno>>() {});
            System.out.println("Alumnos cargados desde el fichero JSON con éxito.");
        } catch (IOException e) {
            System.err.println("Error al cargar los alumnos desde el fichero JSON: " + e.getMessage());
        }

        return listaAlumnos; // Devolvemos la lista cargada
    }

    public static void guardarAlumnosEnXml(List<Alumno> listaAlumnos, File archivo) {
        XmlMapper xmlMapper = new XmlMapper(); // Crear un XmlMapper
        xmlMapper.registerModule(new JavaTimeModule());

        try {
            // Escribimos la lista de alumnos en el archivo XML
            xmlMapper.writeValue(archivo, listaAlumnos);
            System.out.println("Alumnos guardados en el fichero XML con éxito.");
        } catch (IOException e) {
            System.err.println("Error al guardar los alumnos en el fichero XML: " + e.getMessage());
        }
    }

    public static List<Alumno> cargarAlumnosDesdeXml(File archivo) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        List<Alumno> listaAlumnos = null;

        try {
            // Cargar la lista de alumnos desde el archivo XML
            listaAlumnos = xmlMapper.readValue(archivo, new TypeReference<List<Alumno>>() {});
            System.out.println("Alumnos cargados desde el fichero XML con éxito.");
        } catch (IOException e) {
            System.err.println("Error al cargar los alumnos desde el fichero XML: " + e.getMessage());
        }

        return listaAlumnos; // Devolver la lista cargada
    }

    /*
    public static void guardarAlumnosEnYaml(List<Alumno> listaAlumnos, File archivo) {
        try {
            // Configurar las opciones de formato de YAML
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // Formato en bloque
            options.setPrettyFlow(true); // Formato legible

            // Crear un representer personalizado para evitar que muestre el tipo de clase
            Representer representer = new Representer(options);
            representer.getPropertyUtils().setSkipMissingProperties(true);

            // Crear el objeto YAML con opciones y representer personalizado
            Yaml yaml = new Yaml(representer, options);

            try (FileWriter writer = new FileWriter(archivo)) {
                yaml.dump(listaAlumnos, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Alumno> cargarAlumnosDesdeYaml(File archivo) {
        Yaml yaml = new Yaml();
        List<Alumno> listaAlumnos = null;

        try (FileReader reader = new FileReader(archivo)) {
            // Cargamos la lista de alumnos desde el archivo YAML
            listaAlumnos = yaml.load(reader);  // Usamos el método load directamente

            // Como SnakeYAML puede devolver un Object, hacemos un cast explícito
            listaAlumnos = (List<Alumno>) yaml.load(reader);
            System.out.println("Alumnos cargados desde el fichero YAML con éxito.");
        } catch (ClassCastException e) {
            System.err.println("Error al convertir los datos desde el YAML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al cargar los alumnos desde el fichero YAML: " + e.getMessage());
        }

        return listaAlumnos; // Devolvemos la lista cargada
    }
    */
}
