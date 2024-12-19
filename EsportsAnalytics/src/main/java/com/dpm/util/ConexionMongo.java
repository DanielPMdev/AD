package com.dpm.util;

/**
 * @author danielpm.dev
 */
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConexionMongo {

    // MongoDB connection
    private static MongoClient mongoClient;
    private static final String URI = "mongodb://root:toor@localhost:27017/";  // URI de conexión de MongoDB
    //private static final String DATABASE_NAME = "ESPORTS";  // Nombre de la base de datos en MongoDB
    private static final String DATABASE_NAME = "ESPORTS_PRUEBAS";  // Nombre de la base de datos en MongoDB

    private ConexionMongo() {
        // Constructor privado para evitar instanciación externa
    }

    public static MongoDatabase getMongoDatabase() {
        try {
            // Verifica si la conexión ya existe
            if (mongoClient == null) {
                mongoClient = MongoClients.create(URI);
            } else {
                System.out.println("There is already an active MongoDB connection...");
            }
        } catch (Exception ex) {
            System.err.println("Database Connection Error: " + ex.getMessage());
            throw new RuntimeException("MongoDB Connection Error", ex);
        }
        // Retornar la base de datos "ESPORTS"
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        // Obtener la base de datos
        MongoDatabase database = getMongoDatabase();
        // Retornar la colección especificada
        return database.getCollection(collectionName);
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB connection closed.");
        }
    }
}