package org.dmp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionOra {

    // Oracle connection
    private static Connection conn;
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String USER = "daniel";
    private static final String PASSWORD = "toor";

    public static Connection getConexion() {
        try {
            // Verifica si la conexión ya existe o está cerrada
            if (conn == null || conn.isClosed()) {
                // Cargar el driver de la base de datos
                Class.forName(DRIVER);
                // Establecer la conexión
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } else {
                System.out.println("There is already an active DB connection...");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionOra.class.getName()).log(Level.SEVERE, "Database Driver Class Not Found", ex);
            throw new RuntimeException("Database Driver Class Not Found", ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionOra.class.getName()).log(Level.SEVERE, "Database Connection Error", ex);
            System.out.printf("AN EXCEPTION HAS OCCURRED:%n");
            System.out.printf("Message: %s %n", ex.getMessage());
            System.out.printf("SQL State: %s %n", ex.getSQLState());
            System.out.printf("Error Code: %s %n", ex.getErrorCode());
            throw new RuntimeException("Database Connection Error", ex);
        }
        return conn;
    }
}


