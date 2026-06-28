package com.upeu.comarket.service;

import com.upeu.comarket.dao.DetalleVentaDao;
import com.upeu.comarket.dao.VentaDao;
import com.upeu.comarket.db.ConexionSQLite;
import com.upeu.comarket.entity.DetalleVenta;
import com.upeu.comarket.entity.Venta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaServiceImplSQLite implements VentaService {
    private final VentaDao ventaDao = new VentaDao();
    private final DetalleVentaDao detalleVentaDao = new DetalleVentaDao();

    @Override
    public void registrar(Venta venta) {
        validarVenta(venta);

        try (Connection connection = ConexionSQLite.obtenerConexion()) {
            connection.setAutoCommit(false);
            try {
                validarStock(connection, venta);
                long ventaId = ventaDao.insertar(connection, venta);
                for (DetalleVenta detalle : venta.getDetalles()) {
                    detalleVentaDao.insertar(connection, ventaId, detalle);
                    ventaDao.descontarStock(connection, detalle);
                }
                connection.commit();
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo registrar la venta.", e);
        }
    }

    @Override
    public double calcularTotal(Venta venta) {
        if (venta == null) {
            return 0;
        }
        return venta.calcularTotal();
    }

    @Override
    public List<Venta> listar() {
        return ventaDao.listar();
    }

    @Override
    public List<DetalleVenta> listarDetalles(long ventaId) {
        return detalleVentaDao.listarPorVentaId(ventaId);
    }

    @Override
    public boolean anular(long ventaId) {
        try (Connection connection = ConexionSQLite.obtenerConexion()) {
            connection.setAutoCommit(false);
            try {
                Venta venta = ventaDao.buscarPorId(connection, ventaId);
                if (venta == null) {
                    connection.rollback();
                    return false;
                }
                if ("ANULADA".equalsIgnoreCase(venta.getEstado())) {
                    throw new IllegalArgumentException("La venta ya esta anulada.");
                }

                List<DetalleVenta> detalles = detalleVentaDao.listarPorVentaId(connection, ventaId);
                for (DetalleVenta detalle : detalles) {
                    ventaDao.reponerStock(connection, detalle);
                }
                ventaDao.anular(connection, ventaId);
                connection.commit();
                return true;
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo anular la venta.", e);
        }
    }

    private void validarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta es obligatoria.");
        }
        if (estaVacio(venta.getCliente())) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }
        if (venta.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Agregue al menos un detalle a la venta.");
        }

        for (DetalleVenta detalle : venta.getDetalles()) {
            if (detalle.getProducto() == null) {
                throw new IllegalArgumentException("Cada detalle debe tener un producto.");
            }
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
            }
            if (detalle.getPrecioUnitario() < 0) {
                throw new IllegalArgumentException("El precio unitario no puede ser negativo.");
            }
        }
    }

    private void validarStock(Connection connection, Venta venta) throws SQLException {
        Map<String, Integer> cantidadesPorProducto = new HashMap<>();
        for (DetalleVenta detalle : venta.getDetalles()) {
            String codigo = detalle.getProducto().getCodigo();
            cantidadesPorProducto.merge(codigo, detalle.getCantidad(), Integer::sum);
        }

        for (Map.Entry<String, Integer> entry : cantidadesPorProducto.entrySet()) {
            int stock = ventaDao.obtenerStock(connection, entry.getKey());
            if (entry.getValue() > stock) {
                throw new IllegalArgumentException("Stock insuficiente para el producto " + entry.getKey());
            }
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
