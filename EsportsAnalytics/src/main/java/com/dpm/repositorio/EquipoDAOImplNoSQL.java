package com.dpm.repositorio;

import com.dpm.modelo.Equipo;
import com.dpm.modelo.Titulo;
import com.dpm.util.ConexionMongo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author danielpm.dev
 */
public class EquipoDAOImplNoSQL implements EquipoDAO {

    public EquipoDAOImplNoSQL() {
    }

    @Override
    public boolean insertarEquipo(Equipo e) {
        // Obtener la colección "equipos" usando la clase Singleton ConexionMongo
        MongoCollection<Document> collection = ConexionMongo.getCollection("equipo");

        try {
            // Crear el documento a insertar
            Document equipoDoc = new Document("_id", e.getId())
                    .append("nombre", e.getNombre())
                    .append("region", e.getRegion())
                    .append("ano_fundacion", e.getAnoFundacion())
                    .append("titulos", e.getTitulos().stream().map(titulo ->
                            new Document("tipo", titulo.getTipo())
                                    .append("cantidad", titulo.getCantidad())
                    ).toList())
                    .append("entrenador", e.getEntrenador())
                    .append("jugadores", e.getJugadores());

            // Insertar el documento en la colección
            collection.insertOne(equipoDoc);

            return true; // Si la inserción fue exitosa
        } catch (Exception ex) {
            ex.printStackTrace();
            return false; // Si ocurrió un error durante la inserción
        }
    }

    @Override
    public boolean eliminarEquipo(Equipo e) {
        // Obtener la colección "equipos" usando la clase Singleton ConexionMongo
        MongoCollection<Document> collection = ConexionMongo.getCollection("equipo");

        try {
            // Crear un filtro para buscar el equipo por su id
            Document filtro = new Document("_id", e.getId());

            // Eliminar el equipo de la colección
            DeleteResult result = collection.deleteOne(filtro);

            // Verificar si se eliminó algún documento
            return result.getDeletedCount() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean actualizarEquipo(Equipo e) {
        // Obtener la colección "equipos" usando la clase Singleton ConexionMongo
        MongoCollection<Document> collection = ConexionMongo.getCollection("equipo");

        try {
            // Crear un filtro para buscar el equipo por su id
            Document filtro = new Document("_id", e.getId());

            // Crear el documento con los nuevos valores para actualizar
            Document equipoDoc = new Document()
                    .append("nombre", e.getNombre())
                    .append("region", e.getRegion())
                    .append("ano_fundacion", e.getAnoFundacion())
                    .append("titulos", e.getTitulos().stream().map(titulo ->
                            new Document("tipo", titulo.getTipo())
                                    .append("cantidad", titulo.getCantidad())
                    ).toList())
                    .append("entrenador", e.getEntrenador())
                    .append("jugadores", e.getJugadores());

            // Crear la operación de actualización
            Document updateOperation = new Document("$set", equipoDoc);

            // Actualizar el documento en la colección
            UpdateResult result = collection.updateOne(filtro, updateOperation);

            // Verificar si se actualizó algún documento
            return result.getMatchedCount() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false; // Si ocurrió un error durante la actualización
        }
    }

    @Override
    public List<Equipo> consultarPorAtributo(String atributo, String valor) {
        List<Equipo> listaEquipos = new ArrayList<>();

        try {
            // Obtener la colección "equipos" usando la clase Singleton ConexionMongo
            MongoCollection<Document> collection = ConexionMongo.getCollection("equipo");

            // Crear el filtro para la consulta
            Bson filtro;
            switch (atributo) {
                case "Region" -> filtro = Filters.eq("region", valor);

                case "AnoFundacion" -> filtro = Filters.eq("ano_fundacion", Integer.parseInt(valor));

                default -> throw new IllegalArgumentException("Atributo no soportado: " + atributo);
            }

            // Ejecutar la consulta en MongoDB
            FindIterable<Document> resultSet = collection.find(filtro);

            // Recorrer los resultados y mapearlos a objetos Equipo
            for (Document doc : resultSet) {
                Equipo equipo = new Equipo();
                equipo.setId(doc.getInteger("_id"));
                equipo.setNombre(doc.getString("nombre"));
                equipo.setRegion(doc.getString("region"));
                equipo.setAnoFundacion(doc.getInteger("ano_fundacion"));

                // Mapear títulos del equipo
                List<Document> titulosDocs = (List<Document>) doc.get("titulos");
                if (titulosDocs != null) {
                    List<Titulo> titulos = new ArrayList<>();
                    for (Document tituloDoc : titulosDocs) {
                        Titulo titulo = new Titulo();
                        titulo.setTipo(tituloDoc.getString("tipo"));
                        titulo.setCantidad(tituloDoc.getInteger("cantidad"));
                        titulos.add(titulo);
                    }
                    equipo.setTitulos(titulos);
                }

                equipo.setEntrenador(doc.getString("entrenador"));

                // Mapear jugadores del equipo
                List<Integer> jugadores = (List<Integer>) doc.get("jugadores");
                if (jugadores != null) {
                    equipo.setJugadores(jugadores);
                }

                // Añadir el equipo a la lista
                listaEquipos.add(equipo);
            }

            System.out.println("Consulta realizada correctamente en MongoDB");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEquipos;
    }

    @Override
    public List<Equipo> mostrarEquipos() {
        List<Equipo> listaEquipos = new ArrayList<>();

        try {
            // Obtener la colección "equipos" usando la clase Singleton ConexionMongo
            MongoCollection<Document> collection = ConexionMongo.getCollection("equipo");

            // Consultar todos los equipos de la colección
            FindIterable<Document> resultSet = collection.find();

            // Recorrer los resultados y mapearlos a objetos Equipo
            for (Document doc : resultSet) {
                Equipo equipo = new Equipo();
                equipo.setId(doc.getInteger("_id"));
                equipo.setNombre(doc.getString("nombre"));
                equipo.setRegion(doc.getString("region"));
                equipo.setAnoFundacion(doc.getInteger("ano_fundacion"));

                // Mapear títulos del equipo
                List<Document> titulosDocs = (List<Document>) doc.get("titulos");
                if (titulosDocs != null) {
                    List<Titulo> titulos = new ArrayList<>();
                    for (Document tituloDoc : titulosDocs) {
                        Titulo titulo = new Titulo();
                        titulo.setTipo(tituloDoc.getString("tipo"));
                        titulo.setCantidad(tituloDoc.getInteger("cantidad"));
                        titulos.add(titulo);
                    }
                    equipo.setTitulos(titulos);
                }

                equipo.setEntrenador(doc.getString("entrenador"));

                // Mapear jugadores del equipo
                List<Integer> jugadores = (List<Integer>) doc.get("jugadores");
                if (jugadores != null) {
                    equipo.setJugadores(jugadores);
                }

                // Añadir el equipo a la lista
                listaEquipos.add(equipo);
            }

            System.out.println("Datos obtenidos correctamente de la base de datos MongoDB");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEquipos;
    }

    @Override
    public boolean exportarDatosAJSON(List<Equipo> listaEquipos, File archivo) {
        boolean exito = false;

        try (FileWriter writer = new FileWriter(archivo)) {
            // Crear una lista para los documentos transformados
            List<Document> documentos = new ArrayList<>();

            for (Equipo equipo : listaEquipos) {
                // Transformar el objeto Equipo en un documento MongoDB
                Document equipoDoc = new Document("_id", equipo.getId())
                        .append("nombre", equipo.getNombre())
                        .append("region", equipo.getRegion())
                        .append("ano_fundacion", equipo.getAnoFundacion());

                // Transformar la lista de títulos
                if (equipo.getTitulos() != null) {
                    List<Document> titulosDocs = new ArrayList<>();
                    for (Titulo titulo : equipo.getTitulos()) {
                        titulosDocs.add(new Document("tipo", titulo.getTipo())
                                .append("cantidad", titulo.getCantidad()));
                    }
                    equipoDoc.append("titulos", titulosDocs);
                }
                equipoDoc.append("entrenador", equipo.getEntrenador())
                        .append("jugadores", equipo.getJugadores());

                // Añadir el documento transformado a la lista
                documentos.add(equipoDoc);
            }

            // Escribir los documentos como un JSON array al archivo
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(documentos));
            exito = true;

            System.out.println("Datos exportados correctamente a " + archivo.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exito;
    }

    @Override
    public boolean exportarDatosACSV(List<Equipo> listaEquipos, File archivo) {
        boolean exito = false;

        try (FileWriter writer = new FileWriter(archivo)) {
            // Escribir la cabecera del CSV con los títulos y jugadores indexados
            writer.append("_id,nombre,region,ano_fundacion,titulos[0].tipo,titulos[1].tipo,titulos[2].tipo,titulos[0].cantidad,titulos[1].cantidad,titulos[2].cantidad,entrenador,jugadores[0],jugadores[1],jugadores[2]\n");

            // Escribir los datos de cada equipo
            for (Equipo equipo : listaEquipos) {
                // Escribir los datos de cada equipo en formato CSV
                writer.append(String.valueOf(equipo.getId()))
                        .append(",")
                        .append(equipo.getNombre())
                        .append(",")
                        .append(equipo.getRegion())
                        .append(",")
                        .append(String.valueOf(equipo.getAnoFundacion()))
                        .append(",");

                // Escribir los títulos (máximo 3 títulos, si existen)
                if (equipo.getTitulos() != null) {
                    for (int i = 0; i < equipo.getTitulos().size(); i++) {
                        writer.append(equipo.getTitulos().get(i).getTipo())
                                .append(",");
                    }
                    for (int i = 0; i < equipo.getTitulos().size(); i++) {
                        writer.append(String.valueOf(equipo.getTitulos().get(i).getCantidad()))
                                .append(",");
                    }

                } else {
                    // Si no hay títulos, se dejan vacíos
                    writer.append(",");
                }


                writer.append(equipo.getEntrenador())
                        .append(",");

                // Escribir los jugadores (máximo 3 jugadores, si existen)
                if (equipo.getJugadores() != null) {
                    for (int i = 0; i < equipo.getJugadores().size(); i++) {
                        writer.append(String.valueOf(equipo.getJugadores().get(i)))
                                .append(",");
                    }
                } else {
                    // Si no hay jugadores, se dejan vacíos
                    writer.append(",");
                }

                writer.append("\n"); // Nueva línea para el siguiente equipo
            }

            exito = true;
            System.out.println("Datos exportados correctamente a " + archivo.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return exito;
    }

    @Override
    public boolean exportarDatosASQL(List<Equipo> listaEquipos, File archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

            // Encabezado del archivo SQL
            writer.write("-- Exportación de datos de la colección Equipo a SQL\n");
            writer.write("-- Tabla: Equipo\n\n");

            for (Equipo equipo : listaEquipos) {
                // Generar valores predeterminados para títulos
                int titulosMsi = 0;
                int titulosWorlds = 0;
                int titulosLigaNacional = 0;

                for (var titulo : equipo.getTitulos()) {
                    switch (titulo.getTipo().toLowerCase()) {
                        case "msi":
                            titulosMsi = titulo.getCantidad();
                            break;
                        case "worlds":
                            titulosWorlds = titulo.getCantidad();
                            break;
                        case "liga nacional":
                            titulosLigaNacional = titulo.getCantidad();
                            break;
                    }
                }

                // Crear sentencia INSERT para la tabla Equipo
                String insertEquipo = String.format(
                        "INSERT INTO Equipo (id_equipo, nombre, region, ano_fundacion, titulos_msi, titulos_worlds, titulos_liga_nacional, entrenador) VALUES (%d, '%s', '%s', %d, %d, %d, %d, '%s');\n",
                        equipo.getId(),
                        equipo.getNombre().replace("'", "''"), // Escapar comillas simples
                        equipo.getRegion().replace("'", "''"),
                        equipo.getAnoFundacion(),
                        titulosMsi,
                        titulosWorlds,
                        titulosLigaNacional,
                        equipo.getEntrenador() != null ? equipo.getEntrenador().replace("'", "''") : "NULL"
                );

                writer.write(insertEquipo);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Equipo> importarDatosDesdeJSON(File archivo) {
        return List.of();
    }
}
