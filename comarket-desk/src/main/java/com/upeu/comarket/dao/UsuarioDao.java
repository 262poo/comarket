package com.upeu.comarket.dao;

import com.upeu.comarket.db.ConexionSQLite;
import com.upeu.comarket.entity.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDao {
    public UsuarioDao() {
        crearTablaSiNoExiste();
        crearUsuarioAdminSiNoExiste();
    }

    public Usuario buscarPorUsername(String username) {
        String sql = """
                SELECT id, username, password_hash, rol
                FROM usuario
                WHERE username = ?
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearUsuario(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo buscar el usuario.", e);
        }
    }

    private void crearTablaSiNoExiste() {
        String sql = """
                CREATE TABLE IF NOT EXISTS usuario (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    rol TEXT NOT NULL
                )
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo preparar la tabla usuario.", e);
        }
    }

    private void crearUsuarioAdminSiNoExiste() {
        if (buscarPorUsername("admin") != null) {
            return;
        }

        String sql = """
                INSERT INTO usuario (username, password_hash, rol)
                VALUES ('admin', '123456', 'ADMIN')
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo crear el usuario inicial.", e);
        }
    }

    private Usuario mapearUsuario(ResultSet resultSet) throws SQLException {
        return new Usuario(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password_hash"),
                resultSet.getString("rol")
        );
    }
}
