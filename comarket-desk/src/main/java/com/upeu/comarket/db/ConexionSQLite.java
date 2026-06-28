package com.upeu.comarket.db;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionSQLite {
    private static final Path DB_PATH = resolverRutaBaseDatos();
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    private ConexionSQLite() {
    }

    public static Connection obtenerConexion() throws SQLException {
        cargarDriver();
        return DriverManager.getConnection(URL);
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
}
