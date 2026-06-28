package com.upeu.comarket.dao;

import com.upeu.comarket.db.ConexionSQLite;
import com.upeu.comarket.entity.DetalleVenta;
import com.upeu.comarket.entity.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDao {
    public void insertar(Connection connection, long ventaId, DetalleVenta detalle) throws SQLException {
        String sql = """
                INSERT INTO detalle_venta (
                    venta_id, producto_codigo, cantidad, precio_unitario, subtotal
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, ventaId);
            statement.setString(2, detalle.getProducto().getCodigo());
            statement.setInt(3, detalle.getCantidad());
            statement.setDouble(4, detalle.getPrecioUnitario());
            statement.setDouble(5, detalle.getSubtotal());
            statement.executeUpdate();
        }
    }

    public List<DetalleVenta> listarPorVentaId(long ventaId) {
        try (Connection connection = ConexionSQLite.obtenerConexion()) {
            return listarPorVentaId(connection, ventaId);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron listar los detalles de la venta.", e);
        }
    }

    public List<DetalleVenta> listarPorVentaId(Connection connection, long ventaId) throws SQLException {
        String sql = """
                SELECT
                    p.codigo,
                    p.nombre,
                    dv.precio_unitario,
                    p.stock,
                    dv.cantidad
                FROM detalle_venta dv
                INNER JOIN producto p ON p.codigo = dv.producto_codigo
                WHERE dv.venta_id = ?
                ORDER BY dv.id
                """;
        List<DetalleVenta> detalles = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, ventaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Producto producto = new Producto(
                            resultSet.getString("codigo"),
                            resultSet.getString("nombre"),
                            resultSet.getDouble("precio_unitario"),
                            resultSet.getInt("stock")
                    );
                    detalles.add(new DetalleVenta(
                            producto,
                            resultSet.getInt("cantidad"),
                            resultSet.getDouble("precio_unitario")
                    ));
                }
                return detalles;
            }
        }
    }
}
