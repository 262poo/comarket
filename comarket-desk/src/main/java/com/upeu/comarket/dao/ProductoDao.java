package com.upeu.comarket.dao;

import com.upeu.comarket.db.ConexionSQLite;
import com.upeu.comarket.entity.Producto;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {
    public ProductoDao() {
        crearDirectorioBaseDatos();
        crearTablaSiNoExiste();
    }

    public void insertar(Producto producto) {
        String sql = """
                INSERT INTO producto (codigo, nombre, precio, stock)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getCodigo());
            statement.setString(2, producto.getNombre());
            statement.setDouble(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo registrar el producto en la base de datos.", e);
        }
    }

    public List<Producto> listar() {
        String sql = """
                SELECT codigo, nombre, precio, stock
                FROM producto
                ORDER BY codigo
                """;
        List<Producto> productos = new ArrayList<>();

        try (Connection connection = ConexionSQLite.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                productos.add(mapearProducto(resultSet));
            }
            return productos;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron listar los productos.", e);
        }
    }

    public Producto buscarPorCodigo(String codigo) {
        String sql = """
                SELECT codigo, nombre, precio, stock
                FROM producto
                WHERE lower(codigo) = lower(?)
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearProducto(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo buscar el producto.", e);
        }
    }

    public boolean actualizar(Producto producto) {
        String sql = """
                UPDATE producto
                SET nombre = ?, precio = ?, stock = ?
                WHERE lower(codigo) = lower(?)
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setString(4, producto.getCodigo());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo actualizar el producto.", e);
        }
    }

    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM producto WHERE lower(codigo) = lower(?)";

        try (Connection connection = ConexionSQLite.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigo);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo eliminar el producto.", e);
        }
    }

    private void crearDirectorioBaseDatos() {
        try {
            Files.createDirectories(ConexionSQLite.obtenerRutaBaseDatos().getParent());
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear el directorio de la base de datos.", e);
        }
    }

    private void crearTablaSiNoExiste() {
        String sql = """
                CREATE TABLE IF NOT EXISTS producto (
                    codigo TEXT PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    precio REAL NOT NULL CHECK (precio >= 0),
                    stock INTEGER NOT NULL CHECK (stock >= 0)
                )
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo preparar la tabla producto.", e);
        }
    }

    private Producto mapearProducto(ResultSet resultSet) throws SQLException {
        return new Producto(
                resultSet.getString("codigo"),
                resultSet.getString("nombre"),
                resultSet.getDouble("precio"),
                resultSet.getInt("stock")
        );
    }
}
