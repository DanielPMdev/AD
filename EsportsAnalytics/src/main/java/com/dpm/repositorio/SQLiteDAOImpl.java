package com.dpm.repositorio;

import com.dpm.util.ConexionSQLite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class SQLiteDAOImpl implements SQLiteDAO {

    private Connection con;

    public SQLiteDAOImpl() {
    }


    @Override
    public boolean crearTablas() {

        try {
            if (con == null || con.isClosed()) {
                con = ConexionSQLite.getConexion();
            }

            String crearTablaEquipo = """
                    CREATE TABLE IF NOT EXISTS Equipo (
                        id_equipo INTEGER PRIMARY KEY,
                        nombre TEXT NOT NULL,
                        region TEXT NOT NULL,
                        ano_fundacion INTEGER NOT NULL,
                        titulos_msi INTEGER DEFAULT 0 CHECK (titulos_msi >= 0),
                        titulos_worlds INTEGER DEFAULT 0 CHECK (titulos_worlds >= 0),
                        titulos_liga_nacional INTEGER DEFAULT 0 CHECK (titulos_liga_nacional >= 0),
                        entrenador TEXT
                    );
                """;

            String crearTablaJugador = """
                    CREATE TABLE IF NOT EXISTS Jugador (
                        id_jugador INTEGER PRIMARY KEY,
                        nombre TEXT NOT NULL,
                        posicion TEXT NOT NULL CHECK (posicion IN ('mid', 'top', 'jungla', 'adc', 'support')),
                        nacionalidad TEXT NOT NULL,
                        kda REAL,
                        cs_por_minuto REAL CHECK (cs_por_minuto >= 0),
                        participacion_kill REAL CHECK (participacion_kill BETWEEN 0 AND 100),
                        id_equipo INTEGER NOT NULL,
                        FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo)
                    );
                """;

            try (Statement stmt = con.createStatement()) {

                // Crear tabla Equipo
                stmt.execute(crearTablaEquipo);

                // Crear tabla Jugador
                stmt.execute(crearTablaJugador);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean eliminarTablas() {
        try {
            if (con == null || con.isClosed()) {
                con = ConexionSQLite.getConexion();
            }

            String eliminarTablaJugador = "DROP TABLE IF EXISTS Jugador;";
            String eliminarTablaEquipo = "DROP TABLE IF EXISTS Equipo;";

            try (Statement stmt = con.createStatement()) {

                // Eliminar tabla Jugador
                stmt.execute(eliminarTablaJugador);

                // Eliminar tabla Equipo
                stmt.execute(eliminarTablaEquipo);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean ejecutarScript() {
        // Eliminar tablas si existen y crear las nuevas
        if (!eliminarTablas() || !crearTablas()) {
            return false;
        }

        // Definir las rutas de los archivos SQL
        File dir = new File("../EsportsAnalytics/archivos");
        File archivoEquipo = new File(dir, "equipo.sql");
        File archivoJugador = new File(dir, "jugador.sql");

        // Ejecutar los scripts de los archivos SQL
        try {
            // Verificar si los archivos existen
            if (!archivoEquipo.exists() || !archivoJugador.exists()) {
                System.out.println("Uno o ambos archivos no existen.");
                return false;
            }

            // Leer y ejecutar el script de equipo.sql
            ejecutarScriptDeArchivo(archivoEquipo);

            // Leer y ejecutar el script de jugador.sql
            ejecutarScriptDeArchivo(archivoJugador);

            return true;

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void ejecutarScriptDeArchivo(File archivo) throws IOException, SQLException {
        // Leer el contenido del archivo SQL
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             Statement stmt = con.createStatement()) {

            String linea;
            StringBuilder sql = new StringBuilder();

            // Leer cada línea y formar el script SQL completo
            while ((linea = br.readLine()) != null) {
                // Eliminar comentarios de las líneas (si es necesario)
                if (linea.trim().startsWith("--")) {
                    continue; // Ignorar comentarios
                }

                // Acumular las líneas del script
                sql.append(linea).append("\n");

                // Ejecutar cuando se llega a un punto y coma que indica el fin de una sentencia
                if (linea.trim().endsWith(";")) {
                    stmt.execute(sql.toString().trim());
                    sql.setLength(0); // Limpiar el buffer para la siguiente sentencia
                }
            }
        }
    }

}
