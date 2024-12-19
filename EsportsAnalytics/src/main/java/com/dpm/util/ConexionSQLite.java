package com.dpm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */

public class ConexionSQLite {

    // SQLite connection
    private static Connection conn;
    private final static String DRIVER = "org.sqlite.JDBC";
    private static final String URL = "jdbc:sqlite:./sqlite/esports.db";
    private static final String USER = "";  // SQLite no necesita usuario
    private static final String PASSWORD = "";  // SQLite no necesita contraseña

    private ConexionSQLite() {

    }

    public static Connection getConexion() {
        try {
            if (conn == null || conn.isClosed()) {
                // Cargar el driver JDBC
                Class.forName(DRIVER);
                // Obtener la conexión (SQLite no usa usuario y contraseña)
                conn = DriverManager.getConnection(URL);
                System.out.println("Conexión establecida con éxito.");
            } else {
                System.out.println("Ya existe una conexión activa a la base de datos...");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionSQLite.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Clase de Driver de Base de Datos no encontrada", ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQLite.class.getName()).log(Level.SEVERE, null, ex);
            System.out.printf("¡Ha ocurrido una excepción!%n");
            System.out.printf("Mensaje: %s %n", ex.getMessage());
            System.out.printf("Estado SQL: %s %n", ex.getSQLState());
            System.out.printf("Código de error: %s %n", ex.getErrorCode());
            throw new RuntimeException("Error en la conexión a la base de datos", ex);
        }
        return conn;
    }

    public static void closeConexion() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Conexión cerrada con éxito.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConexionSQLite.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al cerrar la conexión a la base de datos.");
            }
        }
    }
}


