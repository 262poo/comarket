package com.upeu.comarket.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexionSQLite {
    private static final Path DB_PATH = resolverRutaBaseDatos();
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    private ConexionSQLite() {
    }

    public static Connection obtenerConexion() throws SQLException {
        cargarDriver();
        crearDirectorioBaseDatos();
        Connection connection = DriverManager.getConnection(URL);
        activarClavesForaneas(connection);
        return connection;
    }

    public static Path obtenerRutaBaseDatos() {
        return DB_PATH;
    }

    private static Path resolverRutaBaseDatos() {
        return resolverDirectorioAplicacion().resolve("data").resolve("comarket.db");
    }

    private static Path resolverDirectorioAplicacion() {
        if (System.getProperty("org.graalvm.nativeimage.imagecode") == null) {
            return Path.of("").toAbsolutePath();
        }

        return ProcessHandle.current()
                .info()
                .command()
                .map(Path::of)
                .map(Path::getParent)
                .orElseGet(() -> Path.of("").toAbsolutePath());
    }

    private static void cargarDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontro el driver JDBC de SQLite.", e);
        }
    }

    private static void crearDirectorioBaseDatos() {
        try {
            Files.createDirectories(DB_PATH.getParent());
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear el directorio de la base de datos.", e);
        }
    }

    private static void activarClavesForaneas(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }
    }
}
