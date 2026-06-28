package com.upeu.comarket.dao;

import com.upeu.comarket.db.ConexionSQLite;
import com.upeu.comarket.entity.DetalleVenta;
import com.upeu.comarket.entity.Usuario;
import com.upeu.comarket.entity.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaDao {
    public VentaDao() {
        new UsuarioDao();
        crearTablasSiNoExisten();
    }

    public long insertar(Connection connection, Venta venta) throws SQLException {
        String sql = """
                INSERT INTO venta (cliente, fecha, total, estado, usuario_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, venta.getCliente());
            statement.setString(2, venta.getFecha().toString());
            statement.setDouble(3, venta.calcularTotal());
            statement.setString(4, "ACTIVA");
            statement.setInt(5, venta.getUsuario().getId());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("No se pudo obtener el ID de la venta.");
            }
        }
    }

    public int obtenerStock(Connection connection, String codigoProducto) throws SQLException {
        String sql = "SELECT stock FROM producto WHERE lower(codigo) = lower(?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigoProducto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("stock");
                }
                throw new SQLException("Producto no encontrado: " + codigoProducto);
            }
        }
    }

    public void descontarStock(Connection connection, DetalleVenta detalle) throws SQLException {
        String sql = """
                UPDATE producto
                SET stock = stock - ?
                WHERE lower(codigo) = lower(?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, detalle.getCantidad());
            statement.setString(2, detalle.getProducto().getCodigo());
            statement.executeUpdate();
        }
    }

    public void reponerStock(Connection connection, DetalleVenta detalle) throws SQLException {
        String sql = """
                UPDATE producto
                SET stock = stock + ?
                WHERE lower(codigo) = lower(?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, detalle.getCantidad());
            statement.setString(2, detalle.getProducto().getCodigo());
            statement.executeUpdate();
        }
    }

    public Venta buscarPorId(Connection connection, long ventaId) throws SQLException {
        String sql = """
                SELECT v.id, v.cliente, v.fecha, v.total, v.estado,
                       u.id AS usuario_id, u.username, u.password_hash, u.rol
                FROM venta v
                LEFT JOIN usuario u ON u.id = v.usuario_id
                WHERE v.id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, ventaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearVenta(resultSet);
                }
                return null;
            }
        }
    }

    public void anular(Connection connection, long ventaId) throws SQLException {
        String sql = "UPDATE venta SET estado = 'ANULADA' WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, ventaId);
            statement.executeUpdate();
        }
    }

    public List<Venta> listar() {
        String sql = """
                SELECT v.id, v.cliente, v.fecha, v.total, v.estado,
                       u.id AS usuario_id, u.username, u.password_hash, u.rol
                FROM venta v
                LEFT JOIN usuario u ON u.id = v.usuario_id
                ORDER BY v.id DESC
                """;
        List<Venta> ventas = new ArrayList<>();

        try (Connection connection = ConexionSQLite.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ventas.add(mapearVenta(resultSet));
            }
            return ventas;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron listar las ventas.", e);
        }
    }

    private void crearTablasSiNoExisten() {
        String ventaSql = """
                CREATE TABLE IF NOT EXISTS venta (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    cliente TEXT NOT NULL,
                    fecha TEXT NOT NULL,
                    total REAL NOT NULL,
                    estado TEXT NOT NULL DEFAULT 'ACTIVA',
                    usuario_id INTEGER,
                    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
                )
                """;

        String detalleSql = """
                CREATE TABLE IF NOT EXISTS detalle_venta (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    venta_id INTEGER NOT NULL,
                    producto_codigo TEXT NOT NULL,
                    cantidad INTEGER NOT NULL,
                    precio_unitario REAL NOT NULL,
                    subtotal REAL NOT NULL,
                    FOREIGN KEY (venta_id) REFERENCES venta(id),
                    FOREIGN KEY (producto_codigo) REFERENCES producto(codigo)
                )
                """;

        try (Connection connection = ConexionSQLite.obtenerConexion();
             Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
            statement.execute(ventaSql);
            agregarColumnaEstadoSiNoExiste(connection);
            agregarColumnaUsuarioSiNoExiste(connection);
            statement.execute(detalleSql);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron preparar las tablas de venta.", e);
        }
    }

    private void agregarColumnaEstadoSiNoExiste(Connection connection) throws SQLException {
        if (existeColumna(connection, "venta", "estado")) {
            return;
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("ALTER TABLE venta ADD COLUMN estado TEXT NOT NULL DEFAULT 'ACTIVA'");
        }
    }

    private void agregarColumnaUsuarioSiNoExiste(Connection connection) throws SQLException {
        if (existeColumna(connection, "venta", "usuario_id")) {
            return;
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("ALTER TABLE venta ADD COLUMN usuario_id INTEGER REFERENCES usuario(id)");
        }
    }

    private boolean existeColumna(Connection connection, String tabla, String columna) throws SQLException {
        String sql = "PRAGMA table_info(" + tabla + ")";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                if (columna.equalsIgnoreCase(resultSet.getString("name"))) {
                    return true;
                }
            }
            return false;
        }
    }

    private Venta mapearVenta(ResultSet resultSet) throws SQLException {
        Usuario usuario = null;
        int usuarioId = resultSet.getInt("usuario_id");
        if (!resultSet.wasNull()) {
            usuario = new Usuario(
                    usuarioId,
                    resultSet.getString("username"),
                    resultSet.getString("password_hash"),
                    resultSet.getString("rol")
            );
        }

        return new Venta(
                resultSet.getLong("id"),
                resultSet.getString("cliente"),
                LocalDate.parse(resultSet.getString("fecha")),
                resultSet.getDouble("total"),
                resultSet.getString("estado"),
                usuario
        );
    }
}
