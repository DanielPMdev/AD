package com.dpm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSystemOracle {
    // Oracle connection
    private static Connection conn;
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String USER = "system";
    private static final String PASSWORD = "toor";

    // Constructor privado
    private ConexionSystemOracle() {
        // Evitar instanciación externa
    }

    public static Connection getConexion() {
        try {
            // Verifica si la conexión ya existe o está cerrada
            if (conn == null || conn.isClosed()) {
                // Cargar el driver de la base de datos
                Class.forName(DRIVER);
                // Establecer la conexión
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection established successfully with user 'system'.");
            } else {
                System.out.println("There is already an active DB connection...");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Driver Class Not Found:");
            System.out.println("Message: " + ex.getMessage());
            throw new RuntimeException("Database Driver Class Not Found", ex);
        } catch (SQLException ex) {
            System.out.println("Database Connection Error:");
            System.out.println("Message: " + ex.getMessage());
            System.out.println("SQL State: " + ex.getSQLState());
            System.out.println("Error Code: " + ex.getErrorCode());
            throw new RuntimeException("Database Connection Error", ex);
        }
        return conn;
    }
}
