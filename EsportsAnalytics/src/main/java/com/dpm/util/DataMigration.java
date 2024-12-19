package com.dpm.util;

/**
 * @author danielpm.dev
 */
import java.sql.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;

public class DataMigration {

    public static void migrateData() throws Exception {
        // Conexión a Oracle
        Connection conn = ConexionOra.getConexion();
        Statement stmt = conn.createStatement();

        // Consultar datos de la tabla Equipo
        ResultSet rsEquipo = stmt.executeQuery("SELECT * FROM ESPORTS.Equipo");
        while (rsEquipo.next()) {
            int idEquipo = rsEquipo.getInt("id_equipo");  // Usar el id de Oracle
            String nombre = rsEquipo.getString("nombre");
            String region = rsEquipo.getString("region");
            int anoFundacion = rsEquipo.getInt("ano_fundacion");
            int titulosMSI = rsEquipo.getInt("titulos_msi");
            int titulosWorlds = rsEquipo.getInt("titulos_worlds");
            int titulosLigaNacional = rsEquipo.getInt("titulos_liga_nacional");
            String entrenador = rsEquipo.getString("entrenador");

            // Crear documento de equipo para MongoDB
            Document equipoDoc = new Document()
                    .append("_id", idEquipo)  // Usar el id de Oracle como _id en MongoDB
                    .append("nombre", nombre)
                    .append("region", region)
                    .append("ano_fundacion", anoFundacion)
                    .append("titulos", new ArrayList<Document>() {{
                        add(new Document("tipo", "MSI").append("cantidad", titulosMSI));
                        add(new Document("tipo", "Worlds").append("cantidad", titulosWorlds));
                        add(new Document("tipo", "Liga Nacional").append("cantidad", titulosLigaNacional));
                    }})
                    .append("entrenador", entrenador)
                    .append("jugadores", new ArrayList<Integer>());  // Inicializar el array de jugadores vacío

            // Insertar documento en MongoDB
            MongoDatabase mongoDb = ConexionMongo.getMongoDatabase();
            MongoCollection<Document> equipoCollection = mongoDb.getCollection("equipo");
            equipoCollection.insertOne(equipoDoc);
        }

        // Consultar datos de la tabla Jugador
        ResultSet rsJugador = stmt.executeQuery("SELECT * FROM ESPORTS.Jugador");
        while (rsJugador.next()) {
            String nombre = rsJugador.getString("nombre");
            String posicion = rsJugador.getString("posicion");
            String nacionalidad = rsJugador.getString("nacionalidad");
            double kda = rsJugador.getFloat("kda");
            kda = (Math.round(kda * 100.0) / 100.0);  // Redondear a dos decimales

            double csPorMinuto = rsJugador.getFloat("cs_por_minuto");
            csPorMinuto = (Math.round(csPorMinuto * 100.0) / 100.0);  // Redondear a dos decimales

            double participacionKill = rsJugador.getFloat("participacion_kill");
            participacionKill = (Math.round(participacionKill * 100.0) / 100.0);  // Redondear a dos decimales

            int idJugador = rsJugador.getInt("id_jugador");  // Usar el id de Oracle
            int idEquipo = rsJugador.getInt("id_equipo");

            // Crear documento de jugador para MongoDB
            Document jugadorDoc = new Document()
                    .append("_id", idJugador)  // Usar el id de Oracle como _id en MongoDB
                    .append("nombre", nombre)
                    .append("posicion", posicion)
                    .append("nacionalidad", nacionalidad)
                    .append("estadisticas", new Document()
                            .append("kda", kda)
                            .append("cs_por_minuto", csPorMinuto)
                            .append("participacion_kill", participacionKill)
                    )
                    .append("id_equipo", idEquipo);  // Relación con el equipo

            // Insertar jugador en la colección Jugador
            MongoCollection<Document> jugadorCollection = ConexionMongo.getCollection("jugador");
            jugadorCollection.insertOne(jugadorDoc);

            // Actualizar el documento del equipo con el ID del jugador insertado
            MongoCollection<Document> equipoCollection = ConexionMongo.getCollection("equipo");
            equipoCollection.updateOne(
                    eq("_id", idEquipo),
                    new Document("$push", new Document("jugadores", idJugador))  // Añadir el ID del jugador al array de jugadores
            );
        }

        conn.close();
    }

    public static void main(String[] args) throws Exception {
        migrateData();
    }
}
