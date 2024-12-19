package com.dpm.repositorio;

import com.dpm.modelo.Equipo;
import com.dpm.modelo.Estadisticas;
import com.dpm.modelo.Jugador;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author danielpm.dev
 */
public class JugadorDAOImplNoSQL implements JugadorDAO {

    public JugadorDAOImplNoSQL() {
    }

    @Override
    public boolean insertarJugador(Jugador j) {
        // Obtener la colección "jugadores" usando la clase Singleton ConexionMongo
        MongoCollection<Document> collection = ConexionMongo.getCollection("jugador");

        try {
            // Crear el documento a insertar con los valores del objeto Jugador
            Document jugadorDoc = new Document("_id", j.getId())
                    .append("nombre", j.getNombre())
                    .append("posicion", j.getPosicion())
                    .append("nacionalidad", j.getNacionalidad())
                    .append("estadisticas", new Document("kda", j.getEstadisticas().getKda())
                            .append("cs_por_minuto", j.getEstadisticas().getCsPorMinuto())
                            .append("participacion_kill", j.getEstadisticas().getParticipacionKill()))
                    .append("id_equipo", j.getIdEquipo());

            // Insertar el documento en la colección
            collection.insertOne(jugadorDoc);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarJugador(Jugador j) {
        // Obtener la colección "jugadores" usando la clase Singleton ConexionMongo
        MongoCollection<Document> collection = ConexionMongo.getCollection("jugador");

        try {
            // Crear un filtro para buscar el jugador por su id
            Document filtro = new Document("_id", j.getId());

            // Eliminar el jugador de la colección
            DeleteResult result = collection.deleteOne(filtro);

            // Verificar si se eliminó algún documento
            return result.getDeletedCount() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarJugador(Jugador j) {
        // Obtener la colección "jugadores" usando la clase Singleton ConexionMongo
        MongoCollection<Document> collection = ConexionMongo.getCollection("jugador");

        try {
            // Crear un filtro para buscar el jugador por su id
            Document filtro = new Document("_id", j.getId());

            // Crear el documento con los nuevos valores para actualizar
            Document jugadorDoc = new Document()
                    .append("nombre", j.getNombre())
                    .append("posicion", j.getPosicion())
                    .append("nacionalidad", j.getNacionalidad())
                    .append("estadisticas", new Document("kda", j.getEstadisticas().getKda())
                            .append("cs_por_minuto", j.getEstadisticas().getCsPorMinuto())
                            .append("participacion_kill", j.getEstadisticas().getParticipacionKill()))
                    .append("id_equipo", j.getIdEquipo());

            // Crear la operación de actualización
            Document updateOperation = new Document("$set", jugadorDoc);

            // Actualizar el documento en la colección
            UpdateResult result = collection.updateOne(filtro, updateOperation);

            // Verificar si se actualizó algún documento
            return result.getMatchedCount() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Jugador> consultarPorAtributo(String atributo, String valor) {
        List<Jugador> listaJugadores = new ArrayList<>();

        try {
            // Obtener la colección "jugador" usando la clase Singleton ConexionMongo
            MongoCollection<Document> collection = ConexionMongo.getCollection("jugador");

            // Crear el filtro para la consulta
            Bson filtro;
            switch (atributo) {
                case "Posicion" -> filtro = Filters.eq("posicion", valor);
                case "Nacionalidad" -> filtro = Filters.eq("nacionalidad", valor);
                default -> throw new IllegalArgumentException("Atributo no soportado: " + atributo);
            }

            // Ejecutar la consulta en MongoDB
            FindIterable<Document> resultSet = collection.find(filtro);

            // Recorrer los resultados y mapearlos a objetos Jugador
            for (Document doc : resultSet) {
                Jugador jugador = new Jugador();
                jugador.setId(doc.getInteger("_id"));
                jugador.setNombre(doc.getString("nombre"));
                jugador.setPosicion(doc.getString("posicion"));
                jugador.setNacionalidad(doc.getString("nacionalidad"));

                // Mapear estadísticas del jugador
                Document estadisticasDoc = (Document) doc.get("estadisticas");
                if (estadisticasDoc != null) {
                    Estadisticas estadisticas = new Estadisticas();
                    estadisticas.setKda(estadisticasDoc.getDouble("kda"));
                    estadisticas.setCsPorMinuto(estadisticasDoc.getDouble("cs_por_minuto"));
                    estadisticas.setParticipacionKill(estadisticasDoc.getInteger("participacion_kill"));
                    jugador.setEstadisticas(estadisticas);
                }

                // Asociar el ID del equipo
                jugador.setIdEquipo(doc.getInteger("id_equipo"));

                // Añadir el jugador a la lista
                listaJugadores.add(jugador);
            }

            System.out.println("Consulta realizada correctamente en MongoDB");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaJugadores;
    }


    @Override
    public List<Jugador> mostrarJugadores() {
        List<Jugador> listaJugadores = new ArrayList<>();

        try {
            // Obtener la colección "jugadores" usando la clase Singleton ConexionMongo
            MongoCollection<Document> collection = ConexionMongo.getCollection("jugador");

            // Consultar todos los jugadores de la colección
            FindIterable<Document> resultSet = collection.find();

            // Recorrer los resultados y mapearlos a objetos Jugador
            for (Document doc : resultSet) {
                Jugador jugador = new Jugador();
                jugador.setId(doc.getInteger("_id"));
                jugador.setNombre(doc.getString("nombre"));
                jugador.setPosicion(doc.getString("posicion"));
                jugador.setNacionalidad(doc.getString("nacionalidad"));

                // Mapear estadísticas del jugador
                Document estadisticasDoc = (Document) doc.get("estadisticas");
                if (estadisticasDoc != null) {
                    Estadisticas estadisticas = new Estadisticas();
                    // Obtener el valor "kda" de MongoDB
                    Object kdaObj = estadisticasDoc.get("kda");
                    if (kdaObj instanceof Double) {
                        estadisticas.setKda((Double) kdaObj);
                    } else if (kdaObj instanceof Integer) {
                        estadisticas.setKda(((Integer) kdaObj).doubleValue());
                    }
                    // Obtener el valor "cs_por_minuto" de MongoDB
                    Object csPorMinutoObj = estadisticasDoc.get("cs_por_minuto");
                    if (csPorMinutoObj instanceof Double) {
                        estadisticas.setCsPorMinuto((Double) csPorMinutoObj);
                    } else if (csPorMinutoObj instanceof Integer) {
                        estadisticas.setCsPorMinuto(((Integer) csPorMinutoObj).doubleValue());
                    }

                    estadisticas.setParticipacionKill(estadisticasDoc.getInteger("participacion_kill"));
                    jugador.setEstadisticas(estadisticas);
                }

                jugador.setIdEquipo(doc.getInteger("id_equipo"));

                // Añadir el jugador a la lista
                listaJugadores.add(jugador);
            }

            System.out.println("Datos obtenidos correctamente de la base de datos MongoDB");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaJugadores;
    }

    @Override
    public boolean exportarDatosAJSON(List<Jugador> listaJugadores, File archivo) {
        boolean exito = false;

        try (FileWriter writer = new FileWriter(archivo)) {
            // Crear una lista para los documentos transformados
            List<Document> documentos = new ArrayList<>();

            for (Jugador jugador : listaJugadores) {
                Document jugadorDoc = new Document("_id", jugador.getId())
                        .append("nombre", jugador.getNombre())
                        .append("posicion", jugador.getPosicion())
                        .append("nacionalidad", jugador.getNacionalidad());

                // Transformar las estadísticas del jugador
                if (jugador.getEstadisticas() != null) {
                    Document estadisticasDoc = new Document(new LinkedHashMap<>());
                    estadisticasDoc.append("kda", jugador.getEstadisticas().getKda());
                    estadisticasDoc.append("cs_por_minuto", jugador.getEstadisticas().getCsPorMinuto());
                    estadisticasDoc.append("participacion_kill", jugador.getEstadisticas().getParticipacionKill());
                    jugadorDoc.append("estadisticas", estadisticasDoc);
                }

                jugadorDoc.append("id_equipo", jugador.getIdEquipo());

                // Añadir el documento transformado a la lista
                documentos.add(jugadorDoc);
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
    public boolean exportarDatosACSV(List<Jugador> listaJugadores, File archivo) {
        boolean exito = false;

        try (FileWriter writer = new FileWriter(archivo)) {
            // Escribir la cabecera del CSV con los títulos y jugadores indexados
            writer.append("_id,nombre,posicion,nacionalidad,estadisticas.kda,estadisticas.cs_por_minuto,estadisticas.participacion_kill,id_equipo\n");

            // Escribir los datos de cada equipo
            for (Jugador jugador : listaJugadores) {
                // Escribir los datos de cada equipo en formato CSV
                writer.append(String.valueOf(jugador.getId()))
                        .append(",")
                        .append(jugador.getNombre())
                        .append(",")
                        .append(jugador.getPosicion())
                        .append(",")
                        .append(jugador.getNacionalidad())
                        .append(",");

                writer.append(String.valueOf(jugador.getEstadisticas().getKda()))
                        .append(",")
                        .append(String.valueOf(jugador.getEstadisticas().getCsPorMinuto()))
                        .append(",")
                        .append(String.valueOf(jugador.getEstadisticas().getParticipacionKill()))
                        .append(",");

                writer.append(String.valueOf(jugador.getIdEquipo()))
                        .append("\n");
            }

            exito = true;
            System.out.println("Datos exportados correctamente a " + archivo.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return exito;
    }

    @Override
    public boolean exportarDatosASQL(List<Jugador> listaJugadores, File archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

            // Encabezado del archivo SQL
            writer.write("-- Exportación de datos de la colección Jugador a SQL\n");
            writer.write("-- Tabla: Jugador\n\n");

            // Usar Locale.USA para asegurarse de que los números se formateen con punto como separador decimal
            for (Jugador jugador : listaJugadores) {
                // Validar estadísticas y valores opcionales
                double kda = jugador.getEstadisticas().getKda();
                double csPorMinuto = jugador.getEstadisticas().getCsPorMinuto();
                int participacionKill = jugador.getEstadisticas().getParticipacionKill();

                // Crear sentencia INSERT para la tabla Jugador
                String insertJugador = String.format(
                        Locale.US, // Forzar formato con punto como separador decimal
                        "INSERT INTO Jugador (id_jugador, nombre, posicion, nacionalidad, kda, cs_por_minuto, participacion_kill, id_equipo) VALUES (%d, '%s', '%s', '%s', %.2f, %.2f, %d, %d);\n",
                        jugador.getId(),
                        jugador.getNombre().replace("'", "''"), // Escapar comillas simples
                        jugador.getPosicion().replace("'", "''"),
                        jugador.getNacionalidad().replace("'", "''"),
                        kda,
                        csPorMinuto,
                        participacionKill,
                        jugador.getIdEquipo()
                );

                writer.write(insertJugador);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }





    @Override
    public List<Jugador> importarDatosDesdeJSON(File archivo) {
        return List.of();
    }
}
